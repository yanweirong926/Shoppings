package com.web.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.RespBean;
import com.web.shopping.entity.ResultPage;
import com.web.shopping.pojo.TbSeller;
import com.web.shopping.service.SellerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * controller
 * @author 严伟榕
 *
 */
@RestController
@Api(tags = "BrandController" ,description = "运营商后台商家管理微服务")
@RequestMapping("/seller-Ms")
public class SellerController {

	@Autowired
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("查询所有商家信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@ApiOperation("分页查询所有商家信息")
	@RequestMapping(value = "/findPage",method = RequestMethod.GET)
	public ResultPage  findPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
									@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize){			
		return sellerService.findPage(pageNum, pageSize);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@ApiOperation("增加商家信息")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public RespBean add(@RequestBody TbSeller seller){
		//密码加密
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(seller.getPassword());//加密
		seller.setPassword(password);
		
		try {
			sellerService.add(seller);
			return RespBean.ok("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RespBean.error("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@ApiOperation("修改商家信息")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public RespBean update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
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
	@ApiOperation("查询单个商家信息")
	@RequestMapping(value = "/findOne",method = RequestMethod.GET)
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation("批量删除商家信息")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public RespBean delete(String [] ids){
		try {
			sellerService.delete(ids);
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
	@ApiOperation("分页查询+模糊查询商家信息")
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public ResultPage search(@RequestBody TbSeller seller, 
								@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum,
								@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize  ){
		System.out.println("###############################################");
		System.out.println("##"+pageNum+"##");
		System.out.println("##"+seller.getName()+"##");
		System.out.println("#############################################");
		return sellerService.findPage(seller, pageNum, pageSize);		
	}
	
}
