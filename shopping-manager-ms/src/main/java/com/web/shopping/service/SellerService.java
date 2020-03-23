package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbSeller;

/**
 * 
 * @author 严伟榕
 *
 */
public interface SellerService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeller> findAll();
	
	
	/**
	 * 返回分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	 * @param seller
	 */
	public void add(TbSeller seller);
	
	
	/**
	 * 修改
	 * @param seller
	 */
	public void update(TbSeller seller);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeller findOne(String id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(String [] ids);

	/**
	 * 分页
	 * @param seller
	 * @param pageNum	当前页 码
	 * @param pageSize	每页记录数
	 * @return
	 */
	public ResultPage findPage(TbSeller seller, int pageNum,int pageSize);
	
	/**
	 * 更新状态
	 * @param sellerId
	 * @param status
	 */
	public void updateStatus(String sellerId,String status);
}
