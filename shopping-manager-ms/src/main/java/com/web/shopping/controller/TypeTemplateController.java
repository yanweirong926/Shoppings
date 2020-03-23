package com.web.shopping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbTypeTemplate;
import com.web.shopping.service.TypeTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "TypeTemplateController" ,description = "运营商后台模板管理微服务")
@RequestMapping("/typeTemplate-Ms")
public class TypeTemplateController {

	
	@Autowired
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有模板信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有规格信息")
	@RequestMapping(value = "/findPage",method = RequestMethod.GET)
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return typeTemplateService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param typeTemplate
	 * @return
	 */
	@ApiOperation("增加模板信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbTypeTemplate typeTemplate){
		System.out.println("============================================");
		System.out.println("====================="+typeTemplate.getName()+"====================");
		System.out.println("============================================");
		try {
			typeTemplateService.add(typeTemplate);
			return RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@ApiOperation("修改模板信息")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public RespBean update(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
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
	@ApiOperation("按id查询模板信息")
	@RequestMapping(value = "/findOne" ,method = RequestMethod.GET)
	public TbTypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除模板信息")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public RespBean delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
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
	@ApiOperation("分页查询和模糊查询模板信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbTypeTemplate typeTemplate, 
								@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
								@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		return typeTemplateService.findPage(typeTemplate, pageNum, pageSize);		
	}
	
	@ApiOperation("按模板id查询所有规格信息")
	@RequestMapping(value = "/findBySpecList",method = RequestMethod.GET)
	public List<Map> findSpecList(Long id){
		return typeTemplateService.findSpecList(id);
	}
}
