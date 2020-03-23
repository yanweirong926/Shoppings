package com.web.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "ItemController" ,description = "运营商后台商品条目管理微服务")
@RequestMapping("/item-ms")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有商品条目信息")
	@GetMapping("/findAll")
	public List<TbItem> findAll(){			
		return itemService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有商品条目信息")
	@GetMapping("/findPage")
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return itemService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@ApiOperation("增加商品条目信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbItem item){
		try {
			itemService.add(item);
			return RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param specification
	 * @return
	 */
	@ApiOperation("修改商品条目信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RespBean update(@RequestBody TbItem item){
		try {
			itemService.update(item);
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
	@ApiOperation("通过id查找商品条目信息")
	@RequestMapping(value = "/findOne",method = RequestMethod.GET)
	public TbItem findOne(Long id){
		return itemService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除商品条目信息")
	@GetMapping("/delete")
	public RespBean delete(Long [] ids){
		try {
			itemService.delete(ids);
			return RespBean.ok("删除成功"); 
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
	@ApiOperation("分页查询和按名字模糊查询商品条目信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbItem item, 
							@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
							@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		System.out.println("###############################################");
		System.out.println("##"+pageNum+"##");
		System.out.println("##"+item.getItemSn()+"##");
		System.out.println("#############################################");
		return itemService.findPage(item, pageNum, pageSize);		
	}
}
