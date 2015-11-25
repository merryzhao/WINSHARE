/*
 * @(#)CreatePresentPassword.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.present;

import java.util.Random;

import com.winxuan.ec.support.util.MagicNumber;
/**
 * 生成礼券激活码
 * @author  HideHai
 * @version 1.0,2011-8-31
 */
public class CreatePresentPassword {
	
	public static String create(int length){
		StringBuffer stringBuffer = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<length;i++){
			stringBuffer.append((char)(MagicNumber.SIXTY_FIVE + random.nextInt(MagicNumber.TWENTY_SIX)));
		}
		
		return stringBuffer.toString();
	} 

}

