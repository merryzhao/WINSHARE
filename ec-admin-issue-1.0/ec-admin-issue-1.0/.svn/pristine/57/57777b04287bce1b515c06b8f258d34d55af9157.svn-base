package com.winxuan.ec.admin.controller.editor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.StringUtils;

/**
 * 编辑器文件管理相关功能
 * @author HideHai
 * @version 1.0,2012-5-22
 **/
@Controller
@RequestMapping("/editor")
public class FilePluginController {

	/**
	 * 图片上传的根目录
	 */
	@Value("${complex_image_address}")
	private String imgUploadRoot;
	/**
	 * 图片URL
	 */
	private String imgShowRoot="http://static.winxuancdn.com/goods";
	/**
	 * 单个文件大小的上限值
	 */
	private long imgMaxSize=1024000;
	/**
	 * 允许的文件扩展名
	 */
	private String allExt="gif,jpg,jpeg,png,bmp";

	@Autowired
	private CommonsMultipartResolver multipartResolver;

	@RequestMapping(value="/file",method=RequestMethod.POST)
	public ModelAndView upload(Long mainId,HttpServletRequest request,HttpServletResponse response) throws JSONException{
		ModelAndView modelAndView = new ModelAndView("/editor/result");
		JSONObject jsonObject = new JSONObject();
		int error = 1;
		try {
			if(!"image".equals(request.getParameter("dir"))){
				throw new IllegalArgumentException(String.format("请选择图片上传!"));
			}
			String showUrl = getFilePath(mainId,request);
			String updatePath = imgUploadRoot +File.separator + showUrl;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
			MultipartFile file = multipartRequest.getFile("imgFile");
			String fileName = file.getOriginalFilename();
			if(StringUtils.isNullOrEmpty(fileName) || fileName.indexOf('.') == -1){
				throw new IllegalArgumentException(String.format("文件名不合法!"));
			}
			long fileSize = file.getSize();
			if(fileSize > imgMaxSize){
				throw new IllegalStateException(String.format("上传文件大小超过限制!{File : %s}",fileName));
			}
			String fileExt = fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();

			List<String> allowExt = Arrays.asList(allExt.split(","));
			if(!allowExt.contains(fileExt)){
				throw new IllegalArgumentException(String.format("文件名扩展名不合法!允许的扩展名:%s",allowExt.toString()));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newName = df.format(new Date())+"_"+new Random().nextInt(MagicNumber.THOUSAND)+"."+fileExt;
			String realFileName = updatePath+File.separator+ newName;
			String showFileName = showUrl+File.separator+ newName;
			File uploadFile = new File(realFileName);
			file.transferTo(uploadFile);
			error = 0;
			jsonObject.put("url",imgShowRoot + showFileName);
		} catch (Exception e) {
			jsonObject.put("message",e.getMessage());
		}
		jsonObject.put("error", error);
		modelAndView.addObject("json", jsonObject);
		return modelAndView;
	}

	/**
	 * 获取文件上传的实际路径
	 * @param seller
	 * @param request
	 * @return
	 */
	private String getFilePath(Long productSaleId,HttpServletRequest request){
		if(!multipartResolver.isMultipart(request)){
			throw new IllegalArgumentException(String.format("请选择需要上传的文件!"));
		}
		File updateRoot = new File(imgUploadRoot);
		if(!updateRoot.isDirectory()){
			throw new IllegalArgumentException(String.format("上传的文件目录不存在，请联系管理员!"));
		}
		if(!updateRoot.canWrite()){
			throw new IllegalArgumentException(String.format("上传的文件目录权限不可写，请联系管理员!"));
		}
		String savePath = imgUploadRoot + File.separator + productSaleId;
		String showUrl = File.separator + productSaleId;
		File savePathDir = new File(savePath);
		if(!savePathDir.exists()){
			savePathDir.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += File.separator + ymd;
		showUrl += File.separator + ymd;
		File dirFile = new File(savePath);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		return showUrl;
	}
}

