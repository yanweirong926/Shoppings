package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbGoodsDescMapper;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbGoodsDescExample;
import com.web.shopping.service.GoodsDescService;

@Service
public class GoodsDescServiceImpl implements GoodsDescService {

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Override
	public List<TbGoodsDesc> findAll() {
		return goodsDescMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbGoodsDesc> page= (Page<TbGoodsDesc>)goodsDescMapper.selectByExample(null);		
		return new ResultPage( page.getResult(),page.getTotal());
	}

	@Override
	public void add(TbGoodsDesc goodsDesc) {
		goodsDescMapper.insert(goodsDesc);

	}

	@Override
	public void update(TbGoodsDesc goodsDesc) {
		goodsDescMapper.updateByPrimaryKey(goodsDesc);

	}

	@Override
	public TbGoodsDesc findOne(Long id) {
		
		return goodsDescMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsDescMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public ResultPage findPage(TbGoodsDesc goodsDesc, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsDescExample example=new TbGoodsDescExample();
		TbGoodsDescExample.Criteria criteria = example.createCriteria();
		
		if(goodsDesc!=null){			
			if(goodsDesc.getIntroduction()!=null && goodsDesc.getIntroduction().length()>0){
				criteria.andIntroductionLike("%"+goodsDesc.getIntroduction()+"%");
			}
			if(goodsDesc.getSpecificationItems()!=null && goodsDesc.getSpecificationItems().length()>0){
				criteria.andSpecificationItemsLike("%"+goodsDesc.getSpecificationItems()+"%");
			}
			if(goodsDesc.getCustomAttributeItems()!=null && goodsDesc.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+goodsDesc.getCustomAttributeItems()+"%");
			}
			if(goodsDesc.getItemImages()!=null && goodsDesc.getItemImages().length()>0){
				criteria.andItemImagesLike("%"+goodsDesc.getItemImages()+"%");
			}
			if(goodsDesc.getPackageList()!=null && goodsDesc.getPackageList().length()>0){
				criteria.andPackageListLike("%"+goodsDesc.getPackageList()+"%");
			}
			if(goodsDesc.getSaleService()!=null && goodsDesc.getSaleService().length()>0){
				criteria.andSaleServiceLike("%"+goodsDesc.getSaleService()+"%");
			}
	
		}
		
		Page<TbGoodsDesc> page= (Page<TbGoodsDesc>)goodsDescMapper.selectByExample(example);		
		return new ResultPage( page.getResult(),page.getTotal());
	}

}
