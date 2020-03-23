package com.web.shopping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbBrandMapper;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.pojo.TbBrandExample;
import com.web.shopping.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		return tbBrandMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public void add(TbBrand brand) {
		tbBrandMapper.insert(brand);
	}

	@Override
	public TbBrand findOne(Long id) {
		return tbBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		tbBrandMapper.updateByPrimaryKey(brand);

	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return tbBrandMapper.selectOptionList();
	}

	@Override
	public ResultPage findPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbBrandExample example = new TbBrandExample();
		TbBrandExample.Criteria criteria = example.createCriteria();
		if(brand !=null) {
			if(brand.getName()!=null&&brand.getName().length()>0) {
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0) {
				criteria.andFirstCharEqualTo(brand.getFirstChar());
			}
		}
		
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(example );
		System.out.println("##### "+page.getResult());
		System.out.println(page.getTotal());
		return new ResultPage(page.getResult(), page.getTotal());
	}

}
