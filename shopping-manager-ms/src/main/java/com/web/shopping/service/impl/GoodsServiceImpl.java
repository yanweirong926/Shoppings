package com.web.shopping.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.mapper.TbBrandMapper;
import com.web.shopping.mapper.TbGoodsDescMapper;
import com.web.shopping.mapper.TbGoodsMapper;
import com.web.shopping.mapper.TbItemCatMapper;
import com.web.shopping.mapper.TbItemMapper;
import com.web.shopping.mapper.TbSellerMapper;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbGoodsExample;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojo.TbItemCat;
import com.web.shopping.pojo.TbItemExample;
import com.web.shopping.pojo.TbSeller;
import com.web.shopping.pojogroup.Goods;
import com.web.shopping.service.GoodsService;

/**
 * 服务实现层
 * @author 严伟榕
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
	 * 返回全部列表
	 * 
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	@Override
	public ResultPage findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public void add(Goods goods) {
		
		goods.getTbGoods().setAuditStatus("0"); // 设置审核的状态
		
		goodsMapper.insert(goods.getTbGoods()); // 插入商品信息
		
		goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());  //设置tb_goods_desc表的外键
		
		goodsDescMapper.insert(goods.getTbGoodsDesc()); // 插入商品的扩展信息
		
		setItemList(goods);//保存sku信息

	}

	private void setItemList(Goods goods) {
		System.out.println("是否启用规格：" + goods.getTbGoods().getIsEnableSpec());
		//判断是否启用规格
		if(goods.getTbGoods().getIsEnableSpec().equals("1")){
			// 启用规格
			// 保存SKU列表的信息:
			for(TbItem item:goods.getItemList()){
				// 设置SKU的数据：
				// item.setTitle(title);
				String title = goods.getTbGoods().getGoodsName();
				Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
				for (String key : map.keySet()) {
					title+= " "+map.get(key);
				}
				//设置SKU的标题
				item.setTitle(title);
				//设置SKU的数据
				setValue(goods,item);
				//添加SKU
				itemMapper.insert(item);
			}
		}else{
			// 没有启用规格
			TbItem item = new TbItem();
			
			item.setTitle(goods.getTbGoods().getGoodsName());
			
			item.setPrice(goods.getTbGoods().getPrice());
			
			item.setNum(999);
			
			item.setStatus("0");
			
			item.setIsDefault("1");
			item.setSpec("{}");
			
			setValue(goods,item);
			itemMapper.insert(item);
		}
		
	}

	//设置SKU的数据
	private void setValue(Goods goods, TbItem item) {
		//获取SKU的图片路径
		List<Map> imageList = JSON.parseArray(goods.getTbGoodsDesc().getItemImages(),Map.class);
		
		if(imageList.size()>0){
			item.setImage((String)imageList.get(0).get("url"));
		}
		
		// 保存三级分类的ID:
		item.setCategoryid(goods.getTbGoods().getCategory3Id());
		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());
		// 设置商品ID
		item.setGoodsId(goods.getTbGoods().getId());
		item.setSellerId(goods.getTbGoods().getSellerId());
		
		TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getTbGoods().getCategory3Id());

		item.setCategory(itemCat.getName());

		TbBrand brand = brandMapper.selectByPrimaryKey(goods.getTbGoods().getBrandId());

		item.setBrand(brand.getName());

		TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getTbGoods().getSellerId());

		item.setSeller(seller.getNickName());
		
	}

	@Override
	public void update(Goods goods) {
		// 修改商品信息
		goods.getTbGoods().setAuditStatus("0");
		goodsMapper.updateByPrimaryKey(goods.getTbGoods());
		// 修改商品扩展信息:
		goodsDescMapper.updateByPrimaryKey(goods.getTbGoodsDesc());
		// 修改SKU信息:
		// 先删除，再保存:
		// 删除SKU的信息:
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getTbGoods().getId());
		itemMapper.deleteByExample(example);
		// 保存SKU的信息
		setItemList(goods);		
	}

	@Override
	public Goods findOne(Long id) {
		Goods goods = new Goods();
		// 查询商品表的信息
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setTbGoods(tbGoods);
		// 查询商品扩展表的信息
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setTbGoodsDesc(tbGoodsDesc);
		
		// 查询SKU表的信息:
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		goods.setItemList(list);
		
		return goods;
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
//			goodsMapper.deleteByPrimaryKey(id);
			//通过修改is_delete来表示删除
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}

	}

	@Override
	public ResultPage findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		TbGoodsExample.Criteria criteria = example.createCriteria();
		
		//criteria.andIsDeleteIsNull();
		
		if(goods!=null){			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				System.out.println("设置sellerId: " + goods.getSellerId());
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}

		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);
		System.out.println("##### "+page.getResult());
		System.out.println(page.getTotal());
		return new ResultPage(page.getResult(), page.getTotal());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			
			tbGoods.setAuditStatus(status);
			
			goodsMapper.updateByPrimaryKey(tbGoods);
		}

	}

	@Override
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status) {
		System.out.println("Goods Id列表：" + ids);
		System.out.println("状态：" + status);
		TbItemExample example=new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(status);//状态
		criteria.andGoodsIdIn(Arrays.asList(ids));//指定条件：SPUID集合
		
		return itemMapper.selectByExample(example);
	}

}
