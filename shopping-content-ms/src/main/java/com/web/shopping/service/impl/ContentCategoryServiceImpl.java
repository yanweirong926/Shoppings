package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbContentCategoryMapper;
import com.web.shopping.pojo.TbContentCategory;
import com.web.shopping.pojo.TbContentCategoryExample;
import com.web.shopping.service.ContentCategoryService;

/**
 * 	服务实现层
 * 	@author 严伟榕
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<TbContentCategory> findAll() {
		
		return contentCategoryMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public void add(TbContentCategory contentCategory) {
		contentCategoryMapper.insert(contentCategory);

	}

	@Override
	public void update(TbContentCategory contentCategory) {
		contentCategoryMapper.updateByPrimaryKey(contentCategory);

	}

	@Override
	public TbContentCategory findOne(Long id) {
		
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			contentCategoryMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public ResultPage findPage(TbContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		TbContentCategoryExample.Criteria criteria = example.createCriteria();
		
		if(contentCategory!=null){			
						if(contentCategory.getName()!=null && contentCategory.getName().length()>0){
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
	
		}
		Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(example);
		return new ResultPage(page.getResult(), page.getTotal());
	}

}
