package com.winxuan.ec.front.controller.customer;

import java.io.File;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.winxuan.ec.exception.ReturnOrderException;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-7
 */
public class MultipartFileUtil {
	
	public  static final long MAX_SIZE = 1024 * 1024; 
	
	public  static final String DIRECTORY_PATH = "/home/www/upload/";
	
	public static final  String PRE_URL = "http://static.winxuancdn.com/";
	
	private String allowedContentType;
	
	public String getAllowedContentType() {
		return allowedContentType;
	}

	public void setAllowedContentType(String allowedContentType) {
		this.allowedContentType = allowedContentType;
	}

	public void validate(MultipartFile file) throws ReturnOrderException { 
        if (file.getSize() > MAX_SIZE){
        	throw new ReturnOrderException(null,"to0 Big");
        }      
        if (!allowedContentType.equals(file.getContentType())) { 
            throw new ReturnOrderException(null,"content?");  
        }
    } 
	public String uploadPic(MultipartFile pic,long itemId) throws ReturnOrderException{
		File file = new File(DIRECTORY_PATH+itemId);
		if(!file.exists()){
			file.mkdir();
		}
			validate(pic);	
		try {
			File newFile = new File(file.getAbsolutePath()+getUrlPath(itemId));
			if(!newFile.exists()){
				newFile.mkdir();
			}
			pic.transferTo(newFile);
		} catch (Exception e) {		
			throw new ReturnOrderException(null,"添加图片失败");
		}
		return PRE_URL+getUrlPath(itemId);
		 
	}
	public String getUrlPath(long orderItemId){
		Date now = new Date();
		return "/"+now.getTime()+".jpg";
	}
}
