package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbSpecificationOptionMapper;

import com.web.shopping.pojo.TbSpecificationOption;
import com.web.shopping.pojo.TbSpecificationOptionExample;

import com.web.shopping.service.SpecificationOptionService;

@Service
public class SpecificationOptionServiceImpl implements SpecificationOptionService {

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	@Override
	public List<TbSpecificationOption> findAll() {
		// TODO Auto-generated method stub
		return specificationOptionMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
			
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecificationOption> page = (Page<TbSpecificationOption>) specificationOptionMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public void add(TbSpecificationOption specificationOption) {
		specificationOptionMapper.insert(specificationOption);

	}

	@Override
	public void update(TbSpecificationOption specificationOption) {
		specificationOptionMapper.updateByPrimaryKey(specificationOption);

	}

	@Override
	public TbSpecificationOption findOne(Long id) {
		
		return specificationOptionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			specificationOptionMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public ResultPage findPage(TbSpecificationOption specificationOption, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		
		if(specificationOption!=null){			
						if(specificationOption.getOptionName()!=null && specificationOption.getOptionName().length()>0){
				criteria.andOptionNameLike("%"+specificationOption.getOptionName()+"%");
			}
	
		}
		
		Page<TbSpecificationOption> page= (Page<TbSpecificationOption>)specificationOptionMapper.selectByExample(example);		
		return new ResultPage( page.getResult(),page.getTotal());
	}

}
