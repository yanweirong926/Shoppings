package com.web.shopping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojogroup.Specification;
import com.web.shopping.service.SpecificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "SpecificationController" ,description = "运营商后台规格管理微服务")
@RequestMapping("/specification-Ms")
public class SpecificationController {

	@Autowired 
	private SpecificationService specificationService;
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有规格信息")
	@GetMapping("/findAll")
	public List<TbSpecification> findAll(){			
		return specificationService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有规格信息")
	@GetMapping("/findPage")
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return specificationService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@ApiOperation("增加规格信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody Specification specification){
		try {
			specificationService.add(specification);
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
	public RespBean update(@RequestBody Specification specification){
		try {
			specificationService.update(specification);
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
	@ApiOperation("按id查询规格信息")
	@GetMapping(value = "/findOne")
	public Specification findOne(Long id){
		return specificationService.findOne(id);		
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
			specificationService.delete(ids);
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
	public ResultPage search(@RequestBody TbSpecification specification, 
							@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
							@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		System.out.println("###############################################");
		System.out.println("##"+pageNum+"##");
		System.out.println("##"+specification.getSpecName()+"##");
		System.out.println("#############################################");
		return specificationService.findPage(specification, pageNum, pageSize);		
	}
	@ApiOperation("查询所有规格信息")
	@GetMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return specificationService.selectOptionList();
	}
}
