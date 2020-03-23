package com.web.shopping.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbItem;

public class Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TbGoods tbGoods;
	
	private TbGoodsDesc tbGoodsDesc;
	
	private List<TbItem> itemList;

	public TbGoods getTbGoods() {
		return tbGoods;
	}

	public void setTbGoods(TbGoods tbGoods) {
		this.tbGoods = tbGoods;
	}

	public TbGoodsDesc getTbGoodsDesc() {
		return tbGoodsDesc;
	}

	public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
		this.tbGoodsDesc = tbGoodsDesc;
	}

	public List<TbItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}
	
}
