package com.web.shopping.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopping.entity.RespBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "UploadController" ,description = "运营商后台广告图片上传管理微服务")
@RestController
public class UploadController {

	@ApiOperation("上传广告图片")
	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	public RespBean uploadFile(MultipartFile file){
		try {
			//设置虚拟的映射路径 ---> D:/file
			String path="D:/file";
			String url = "";
			if (file!=null && file.getSize()>0) {
				file.transferTo(new File(path, file.getOriginalFilename()));
				url = "http://localhost:8887/upload/"+file.getOriginalFilename();
			}	
			return RespBean.ok(url);
		} catch (IOException e) {
			e.printStackTrace();
			return RespBean.error("上传失败");
		}
		
	}
}
