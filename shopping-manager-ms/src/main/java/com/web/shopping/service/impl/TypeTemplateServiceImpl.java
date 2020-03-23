package com.web.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbSpecificationOptionMapper;
import com.web.shopping.mapper.TbTypeTemplateMapper;
import com.web.shopping.pojo.TbSpecificationOption;
import com.web.shopping.pojo.TbSpecificationOptionExample;
import com.web.shopping.pojo.TbTypeTemplate;
import com.web.shopping.pojo.TbTypeTemplateExample;
import com.web.shopping.service.TypeTemplateService;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 分页查询
	 */
	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	/**
	 * 	增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);

	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}
	/**
	 * 根据ID获取实体
	 * 
	 */
	@Override
	public TbTypeTemplate findOne(Long id) {
		
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			typeTemplateMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public ResultPage findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		TbTypeTemplateExample.Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){	
			if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
		
		}

		Page<TbTypeTemplate> page= (Page<TbTypeTemplate>)typeTemplateMapper.selectByExample(example);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public List<Map> findSpecList(Long id) {
		//根据ID查询到模板对象
		TbTypeTemplate TypeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		// 获得规格的数据spec_ids
		String spec_ids = TypeTemplate.getSpecIds();// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
		// 将specIds的字符串转成JSON的List<Map>
		List<Map> list = JSON.parseArray(spec_ids, Map.class);
		// 获得每条记录:
		for (Map map : list) {
			// 根据规格的ID 查询规格选项的数据:
			// 设置查询条件:
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));
			
			List<TbSpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
		
			map.put("options", specOptionList);//{"id":27,"text":"网络",options:[{id：xxx,optionName:移动2G}]}
		}
		return list;
	}

}
