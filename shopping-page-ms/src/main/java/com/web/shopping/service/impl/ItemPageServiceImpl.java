package com.web.shopping.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.web.shopping.mapper.TbGoodsDescMapper;
import com.web.shopping.mapper.TbGoodsMapper;
import com.web.shopping.mapper.TbItemCatMapper;
import com.web.shopping.mapper.TbItemMapper;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojo.TbItemExample;
import com.web.shopping.service.ItemPageService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	//@Value("${pagedir}")
	private String pagedir="d:\\item\\";
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public boolean genItemHtml(Long goodsId) {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		
		try {
			Template template = configuration.getTemplate("item.ftl");
			//创建数据模型
			Map dataModel = new HashMap<>();
			//1.保存商品主表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);
			//2.保存商品扩展表数据
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			//3.读取商品分类
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);
			
			//4.读取SKU列表
			TbItemExample example = new TbItemExample();
			TbItemExample.Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);//SKU Id
			criteria.andStatusEqualTo("1");//状态有效			
			example.setOrderByClause("is_default desc");//按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
			
			List<TbItem> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);
			
			Writer out = new FileWriter(pagedir+goodsId+".html");
			template.process(dataModel, out);
			out.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
	}

	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();		
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
