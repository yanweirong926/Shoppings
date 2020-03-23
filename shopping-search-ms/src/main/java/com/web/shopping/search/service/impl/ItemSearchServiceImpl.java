package com.web.shopping.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.web.shopping.pojo.TbItem;
import com.web.shopping.search.service.ItemSearchService;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Override
	public Map search(Map searchMap) {
		Map map=new HashMap();
		
		Query query=new SimpleQuery("*:*");
		//Query query = new SimpleQuery("*:*",pageable );
		//item_keywords是solr的业务字段
		if(searchMap.get("keywords")!=null&&!searchMap.get("keywords").equals("")) {
			Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
			query.addCriteria(criteria);
		}
		
		
		PageRequest pr= new PageRequest((int)searchMap.get("pageNum"), (int) searchMap.get("pageSize"));
		query.setPageRequest(pr);
		ScoredPage<TbItem> page = solrTemplate.queryForPage("collection1",query, TbItem.class);
		
		map.put("rows", page.getContent());
		System.out.println("size:"+page.getSize());
		map.put("pageSize", page.getSize());
		System.out.println("num:"+page.getNumber());
		map.put("pageNum", page.getNumber());
		System.out.println("totals:"+page.getTotalElements());
		map.put("pageTotal", page.getTotalElements());
		return map;
	}

	@Override
	public void importList(List list) {
		solrTemplate.saveBeans("collection1", list);
		solrTemplate.commit("collection1");

	}

	@Override
	public void deleteByGoodsIds(List goodsIds) {
		Query query=new SimpleQuery("*:*");		
		Criteria criteria=new Criteria("item_goodsid").in(goodsIds);
		query.addCriteria(criteria);		
		solrTemplate.delete("collection1",query);
		solrTemplate.commit("collection1");

	}

}
