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
import com.web.shopping.pojo.TbSpecificationOption;
import com.web.shopping.service.SpecificationOptionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "SpecificationOptionController" ,description = "运营商后台规格管理微服务")
@RequestMapping("/specificationOption-Ms")
public class SpecificationOptionController {

	@Autowired 
	private SpecificationOptionService specificationOptionService;
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有规格信息")
	@GetMapping("/findAll")
	public List<TbSpecificationOption> findAll(){			
		return specificationOptionService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有规格信息")
	@GetMapping("/findPage")
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return specificationOptionService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@ApiOperation("增加规格信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.add(specificationOption);
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
	@ApiOperation("修改规格信息")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RespBean update(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.update(specificationOption);
			return RespBean.ok("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("修改失败");
		}
	}	
	
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除规格信息")
	@GetMapping("/delete")
	public RespBean delete(Long [] ids){
		try {
			specificationOptionService.delete(ids);
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
	@ApiOperation("分页查询和按名字模糊查询规格信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbSpecificationOption specificationOption, 
							@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
							@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		System.out.println("###############################################");
		System.out.println("##"+pageNum+"##");
		System.out.println("##"+specificationOption.getOptionName()+"##");
		System.out.println("#############################################");
		return specificationOptionService.findPage(specificationOption, pageNum, pageSize);		
	}
}
