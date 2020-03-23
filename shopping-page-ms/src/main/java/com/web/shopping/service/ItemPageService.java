package com.web.shopping.service;

/**
 * 
 * @author 严伟榕
 *
 */
public interface ItemPageService {

	/**
	 * 生成商品详细页
	 * @param goodsId
	 * @return
	 */
	public boolean genItemHtml(Long goodsId);
	
	/**
	 * 删除商品详细页
	 * @param goodsIds
	 * @return
	 */
	public boolean deleteItemHtml(Long [] goodsIds);
}
