package com.web.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbSpecificationMapper;
import com.web.shopping.mapper.TbSpecificationOptionMapper;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojo.TbSpecificationExample;
import com.web.shopping.pojo.TbSpecificationOption;
import com.web.shopping.pojo.TbSpecificationOptionExample;
import com.web.shopping.pojogroup.Specification;
import com.web.shopping.service.SpecificationService;

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	@Override
	public List<TbSpecification> findAll() {
		
		return specificationMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page =  (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	/**
	 * 增加
	 * 
	 */
	@Override
	public void add(Specification specification) {
		//保存规格
		specificationMapper.insert(specification.getSpecification());
		//保存规格选项
		for(TbSpecificationOption specificationOption:specification.getSpecificationOptionList()) {
			//设置规格id
			specificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(specificationOption);
		}
		
	}

	/**
	 * id查找
	 */
	@Override
	public Specification findOne(Long id) {
		Specification specification = new Specification();
		
		//根据规格id查询规格对象
		TbSpecification tbSpecification =  specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);
		
		//根据规格id查询规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
		specification.setSpecificationOptionList(specificationOptions);
		return specification;
	}

	/**
	 * 	修改
	 */
	@Override
	public void update(Specification specification) {
		//修改规格
		specificationMapper.updateByPrimaryKey(specification.getSpecification());
		
		//先删除规格选项，再保存新的规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(specification.getSpecification().getId());
		specificationOptionMapper.deleteByExample(example);
		
		//保存规格选项
		for(TbSpecificationOption specificationOption:specification.getSpecificationOptionList()) {
			//设置规格id
			specificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(specificationOption);
		}
		
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			//删除规格
			specificationMapper.deleteByPrimaryKey(id);
			
			//删除规格选项
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
			
		}

	}

	/**
	 * 分页模糊查询
	 */
	@Override
	public ResultPage findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example = new TbSpecificationExample();
		TbSpecificationExample.Criteria criteria = example.createCriteria();
		
		if (specification!=null) {
			if (specification.getSpecName()!=null &&specification.getSpecName().length()>0) {
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
		}
		
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example );
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return specificationMapper.selectOptionList();
	}

}
