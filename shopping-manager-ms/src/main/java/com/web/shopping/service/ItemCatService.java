package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbItemCat;

/**
 * ItemCatService
 * @author 严伟榕
 *
 */
public interface ItemCatService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItemCat> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbItemCat itemCat);
	
	
	/**
	 * 修改
	 */
	public void update(TbItemCat itemCat);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItemCat findOne(Long id);
	
	
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
	public ResultPage findPage(TbItemCat itemCat, int pageNum,int pageSize);
	
	
	
	/**
	 * 根据父ID查询分类的方法
	 * @param parentId
	 * @return
	 */
	public List<TbItemCat> findByParentId(Long parentId);
}
