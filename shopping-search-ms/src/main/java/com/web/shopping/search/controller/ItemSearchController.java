package com.web.shopping.search.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.search.service.ItemSearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "ItemSearchController" ,description = "商品搜索管理微服务")
@RequestMapping("/itemsearch-ms")
public class ItemSearchController {

	@Autowired
	private ItemSearchService itemSearchService;
	
	@ApiOperation("搜索商品信息")
	@RequestMapping(value = "search",method = RequestMethod.POST)
	public Map search(@RequestBody Map searchMap) {
		return itemSearchService.search(searchMap);
	}
}
