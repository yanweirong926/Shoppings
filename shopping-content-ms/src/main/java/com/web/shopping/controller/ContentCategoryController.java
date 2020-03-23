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
import com.web.shopping.pojo.TbContentCategory;
import com.web.shopping.service.ContentCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "ContentCategoryController" ,description = "运营商后台广告类别管理微服务")
@RequestMapping("/contentCategory-ms")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有广告信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<TbContentCategory> findAll(){			
		return contentCategoryService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有广告信息")
	@RequestMapping(value = "/findPage",method = RequestMethod.GET)
	public	ResultPage findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return contentCategoryService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param contentCategory
	 * @return
	 */
	@ApiOperation("增加广告信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbContentCategory contentCategory){
		try {
			contentCategoryService.add(contentCategory);
			return  RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param contentCategory
	 * @return
	 */
	@ApiOperation("修改广告信息")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public RespBean update(@RequestBody TbContentCategory contentCategory){
		try {
			contentCategoryService.update(contentCategory);
			return  RespBean.ok("修改成功");
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
	public TbContentCategory findOne(Long id){
		return contentCategoryService.findOne(id);		
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
			contentCategoryService.delete(ids);
			return  RespBean.ok("删除成功");
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
	public ResultPage search(@RequestBody TbContentCategory contentCategory, 
								@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize ){
		return contentCategoryService.findPage(contentCategory, pageNum, pageSize);		
	}
}
