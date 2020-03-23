package com.web.shopping.controller;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.web.shopping.config.JmsConfig;
import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojogroup.Goods;
import com.web.shopping.service.GoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "GoodsController" ,description = "运营商后台商品管理微服务")
@RequestMapping("/goods-ms")
public class GoodsController {

	
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有商品信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有商品信息")
	@RequestMapping(value = "/findPage",method = RequestMethod.GET)
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize ){			
		return goodsService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@ApiOperation("添加商品信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody Goods goods){
		System.out.println("#######"+goods.getTbGoods().getGoodsName()+"#######");
		System.out.println("#######"+goods.getTbGoodsDesc().getItemImages());
		System.out.println("#######"+goods.getTbGoodsDesc().getSpecificationItems());
		System.out.println("#######"+goods.getTbGoodsDesc().getCustomAttributeItems());
		
		try {
			// 获得商家信息:
			//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			String sellerId = "yqtech"; //暂时设定
			goods.getTbGoods().setSellerId(sellerId);
			
			goodsService.add(goods);
			return RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@ApiOperation("修改商品信息")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public RespBean update(@RequestBody Goods goods){
		// 获得商家信息:
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("################################################");
		System.out.println(goods.getTbGoodsDesc().getSpecificationItems());
		System.out.println("################################################");
		String sellerId = "yqtech"; //暂时设定
		Goods goods2 = goodsService.findOne(goods.getTbGoods().getId());
		if(!sellerId.equals(goods2.getTbGoods().getSellerId()) || !sellerId.equals(goods.getTbGoods().getSellerId())){
			return RespBean.error( "非法操作");
		}
		
		try {
			goodsService.update(goods);
			return RespBean.ok("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@ApiOperation("按商品id查询商品信息")
	@RequestMapping(value = "/findOne",method = RequestMethod.GET)
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除商品信息")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public RespBean delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return RespBean.ok( "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("删除失败");
		}
	}
	
	/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@ApiOperation("模糊查询和分页查询所有商品信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbGoods goods,
								@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
								@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		
		
		if(goods != null&& goods.getAuditStatus() == null) {
			//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			String sellerId = "yqtech"; //暂时设定
			goods.setSellerId(sellerId);
		}
		
		
		return goodsService.findPage(goods, pageNum, pageSize);		
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@ApiOperation("更新商品信息状态")
	@RequestMapping(value = "/updateStatus",method = RequestMethod.GET)
	public RespBean updateStatus(Long[] ids,String status){
		System.out.println("ids: " + ids);
		System.out.println("status: " + status);
		try {
			goodsService.updateStatus(ids, status);
			
			if("1".equals(status)){//如果是审核通过 
				//*****导入到索引库
				//得到需要导入的SKU列表
				List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);
				//导入到solr
				//itemSearchService.importList(itemList);	
				final String jsonString = JSON.toJSONString(itemList);//转换为json传输
				System.out.println("Goods conroller: " + jsonString);
				Topic topicSolrDestination = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
				jmsTemplate.send(topicSolrDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						
						return session.createTextMessage(jsonString);
					}
				});
				
				//****生成商品详细页
				for(final Long goodsId:ids){
					Topic topicPageDestination = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
					jmsTemplate.send(topicPageDestination, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {							
							return session.createTextMessage(goodsId+"");
						}
					});					
				}
				
			}		
			
			return RespBean.ok("修改状态成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("修改状态失败");
		}
	}
}
