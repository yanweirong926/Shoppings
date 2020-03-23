package com.web.shopping.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojo.TbSpecificationOption;

public class Specification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
	
	
}
