package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbItemCatMapper;
import com.web.shopping.pojo.TbItemCat;
import com.web.shopping.pojo.TbItemCatExample;
import com.web.shopping.service.ItemCatService;

/**
 * 	服务实现层
 * @author 严伟榕
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 	分页查询全部
	 */
	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		
		return new ResultPage(page.getResult(), page.getTotal());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);

	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat) {
	
		itemCatMapper.updateByPrimaryKey(itemCat);

	}

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id) {
		
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			itemCatMapper.deleteByPrimaryKey(id);
		}

	}

	
	@Override
	public ResultPage findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
			if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)itemCatMapper.selectByExample(example);		
		return new ResultPage( page.getResult(),page.getTotal());
	}

	/**
	 * 根据父ID查询分类的方法
	 * @param parentId
	 * @return
	 */
	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		// 设置条件:
		criteria.andParentIdEqualTo(parentId);
		// 条件查询
		return itemCatMapper.selectByExample(example);
	}

}
