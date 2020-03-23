package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojogroup.Goods;

/**
 *	服务层接口
 * @author 严伟榕
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	 * @param goods
	 */
	public void add(Goods goods);
	
	
	/**
	 * 修改
	 * @param goods
	 */
	public void update(Goods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Goods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPage findPage(TbGoods goods, int pageNum,int pageSize);
	/**
	 * 
	 * @param ids
	 * @param status
	 */
	public void updateStatus(Long[] ids,String status);


	/**
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
}
