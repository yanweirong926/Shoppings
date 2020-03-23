package com.web.shopping.service;

import java.util.List;
import java.util.Map;

import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojogroup.Specification;



public interface SpecificationService {
	
	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	/**
	 * 规格分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPage findPage(int pageNum,int pageSize);
	
	/**
	 * 增加 
	 * @param brand
	 */
	public void add(Specification specification);
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Specification findOne(Long id);
	
	/**
	 * 修改
	 * @param brand
	 */
	public void update(Specification specification);
	
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long[] ids);
	
	
	/**
	 * 规格分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPage findPage(TbSpecification specification, int pageNum,int pageSize);
	
	/**
	 * 返回下拉列表数据
	 * @return
	 */
	public List<Map> selectOptionList();
}
