package com.winxuan.ec.task.job.image;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.image.CompressImageService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-14
 */
@Component("compressImage")
public class CompressImage implements TaskAware, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8964165690094545833L;

	@Autowired
	private CompressImageService compressImageService;
	
	@Override
	public String getName() {
		return "compressImage";
	}

	@Override
	public String getDescription() {
		return "压缩大、中、小三种规格的图片";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	} 

	@Override
	public void start() {
		
		Thread thread = new Thread(new CompressImageThread());
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * 定义压缩图片线程，将图片压缩由单线程转换为多线程
	 * @author monica
	 *
	 */
	public class CompressImageThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			compressImageService.compress();
			compressImageService.compressOther();
		}
		
	}
	
}
