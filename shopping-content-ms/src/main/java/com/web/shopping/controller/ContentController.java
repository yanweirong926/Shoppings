package com.web.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbContent;
import com.web.shopping.service.ContentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * controller
 * @author 严伟榕
 *
 */

@RestController
@Api(tags = "ContentController" ,description = "运营商后台广告管理微服务")
@RequestMapping("/content-ms")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@ApiOperation("按广告类别id查询所有广告信息")
	@RequestMapping(value = "/findByCategoryId",method = RequestMethod.GET)
	public List<TbContent> findByCategoryId(Long categoryId){
		return contentService.findByCategoryId(categoryId);
	}
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有广告信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<TbContent> findAll(){			
		return contentService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有广告信息")
	@RequestMapping(value = "/findPage",method = RequestMethod.GET)
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return contentService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@ApiOperation("增加广告信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbContent content){
		try {
			contentService.add(content);
			return RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@ApiOperation("修改广告信息")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public RespBean update(@RequestBody TbContent content){
		try {
			contentService.update(content);
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
	@ApiOperation("按id查询广告信息")
	@RequestMapping(value = "/findOne",method = RequestMethod.GET)
	public TbContent findOne(Long id){
		return contentService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除广告信息")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public RespBean delete(Long [] ids){
		try {
			contentService.delete(ids);
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
	@ApiOperation("分页查询和模糊查询广告信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbContent content,
						@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
							@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		return contentService.findPage(content, pageNum, pageSize);		
	}
}
