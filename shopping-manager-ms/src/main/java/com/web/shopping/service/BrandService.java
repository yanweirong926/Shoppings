package com.web.shopping.service;

import java.util.List;
import java.util.Map;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbBrand;

public interface BrandService {

	public List<TbBrand> findAll();
	
	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	/**
	 * 增加 
	 * @param brand
	 */
	public void add(TbBrand brand);
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public TbBrand findOne(Long id);
	
	/**
	 * 修改
	 * @param brand
	 */
	public void update(TbBrand brand);
	
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long[] ids);
	
	
	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPage findPage(TbBrand brand, int pageNum,int pageSize);
	
	/**
	 * 返回下拉列表数据
	 * @return
	 */
	public List<Map> selectOptionList();
}
