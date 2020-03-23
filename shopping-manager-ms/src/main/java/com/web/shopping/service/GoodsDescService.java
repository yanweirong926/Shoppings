package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbGoodsDesc;
/**
 * 
 * @author 严伟榕
 *
 */
public interface GoodsDescService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoodsDesc> findAll();
	
	
	/**
	 * 返回分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	 * @param goodsDesc
	 */
	public void add(TbGoodsDesc goodsDesc);
	
	
	/**
	 * 修改
	 * @param goodsDesc
	 */
	public void update(TbGoodsDesc goodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoodsDesc findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param goodsDesc
	 * @param pageNum	当前页 码
	 * @param pageSize	每页记录数
	 * @return
	 */
	public ResultPage findPage(TbGoodsDesc goodsDesc, int pageNum,int pageSize);
}
