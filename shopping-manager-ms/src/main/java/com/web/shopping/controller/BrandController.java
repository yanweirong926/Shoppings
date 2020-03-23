package com.web.shopping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "BrandController" ,description = "运营商后台品牌管理微服务")
@RequestMapping("/brand-Ms")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有品牌信息")
	@GetMapping("/findAll")
	public List<TbBrand> findAll(){			
		return brandService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有品牌信息")
	@GetMapping("/findPage")
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return brandService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@ApiOperation("增加品牌信息")
	@RequestMapping("/add")
	public RespBean add(@RequestBody TbBrand tbBrand){
		try {
			brandService.add(tbBrand);
			return  RespBean.ok("增加成功");
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
	@ApiOperation("修改品牌信息")
	@RequestMapping("/update")
	public RespBean update(@RequestBody TbBrand tbBrand){
		try {
			brandService.update(tbBrand);
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
	@ApiOperation("通过id查询品牌信息")
	@GetMapping("/findOne")
	public TbBrand findOne(Long id){
		return brandService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("删除品牌信息")
	@RequestMapping("/delete")
	public RespBean delete(Long [] ids){
		try {
			brandService.delete(ids);
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
	@ApiOperation("按条件分页查询品牌信息")
	@RequestMapping("/search")
	public ResultPage search(@RequestBody TbBrand tbBrand, int page, int rows  ){
		System.out.println("###############################################");
		System.out.println("##"+page+"##");
		System.out.println("##"+tbBrand+"##");
		System.out.println("#############################################");
		return brandService.findPage(tbBrand, page, rows);		
	}
	@ApiOperation("查询所有品牌id和名字信息")
	@GetMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return brandService.selectOptionList();
	}
}
