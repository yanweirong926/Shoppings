package com.web.shopping.entity;

import java.io.Serializable;
import java.util.List;

/**
 *  封装angular的响应分页数据
 * @author 严伟榕
 *
 */
public class ResultPage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List rows;		//分页区间数
	
	private Long total;		//总记录数

	
	
	public ResultPage() {
		super();
	}

	public ResultPage(List rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	

}
