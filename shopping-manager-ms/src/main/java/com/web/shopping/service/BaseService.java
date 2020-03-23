package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.ResultPage;

public interface BaseService<T> {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<T> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(T t);
	
	
	/**
	 * 修改
	 */
	public void update(T t);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public T findOne(Long id);
	
	
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
	public ResultPage findPage(T t, int pageNum,int pageSize);
}
