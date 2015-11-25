/*
 * @(#)PresentCardUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.presentcard;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.framework.util.security.DESUtils;
import com.winxuan.framework.util.security.RSAUtils;


/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */
public class PresentCardUtils {

	public static Long electronicCategory;
	public static Long physicalCategory;

	private static final Log LOG = LogFactory.getLog(PresentCardUtils.class);

	private static final int RANDOM_LENGTH = 8;
	private static String desKeyPath;
	private static String rsaPublicKeyPath;
	private static String rsaPrivateKeyPath;


	static {
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = PresentCardUtils.class.getClassLoader().getResourceAsStream("presentcard_ca.properties");
			props.load(input);
			desKeyPath = props.getProperty("DES_KEY_PATH");
			LOG.info("presentcard path: " +desKeyPath);
			rsaPublicKeyPath =props.getProperty("RSA_PUBLIC_KEY_PATH");
			rsaPrivateKeyPath =props.getProperty("RSA_PRIVATE_KEY_PATH");
			electronicCategory = new Long(props.getProperty("ELECTRONIC_CATEGORY"));
			physicalCategory = new Long(props.getProperty("PHYSICAL_CATEGORY"));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		} finally {
			try {
				if (input != null){
					input.close();
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * 生成随机密码（加密过后的）
	 * @return
	 */
	public static byte[] generatePassword() {
		return generatePassword(RandomStringUtils.random(RANDOM_LENGTH,"23456789QWERTYUPASDFGHJKLZXCVBNM"));
	}

	public static byte[] generatePassword(String password) {
		return DESUtils.encrypt(password, desKeyPath);
	}

	/**
	 * DES验证
	 * @param proclaimString 明文
	 * @param encryptedBytes 加密后的二进制数组
	 * @return
	 */
	public static boolean desVerify(String proclaimString, byte[] encryptedBytes) {
		String tempPassword = new String(DESUtils.decrypt(encryptedBytes, desKeyPath));
		if(proclaimString.equalsIgnoreCase(tempPassword)){
			return true;
		}
		return false;
	}

	/**
	 * 得到密码明文
	 * @param presentCard
	 * @return
	 */
	public static String getProclaimPassword(PresentCard presentCard) {
		String tempPassword = new String(DESUtils.decrypt(presentCard.getPassword(), desKeyPath));
		return tempPassword;
	}

	/**
	 * 生成校验码
	 * @param presentCard
	 * @return
	 */
	public static String generateVerifyCode(PresentCard presentCard) {
		String proclaimVerifyCode = presentCard.getProclaimVerifyCode();
		return RSAUtils.encrypt(proclaimVerifyCode, rsaPublicKeyPath);
	}
	/**
	 * rsa加密
	 * @param value
	 * @return
	 */
	public static String encrypt(String value){
		return RSAUtils.encrypt(value, rsaPublicKeyPath);
	}
	/**
	 *  解密校验码
	 * @param verifyCode
	 * @return
	 */
	public static String decryptVerifyCode(String verifyCode) {
		return RSAUtils.decrypt(verifyCode, rsaPrivateKeyPath);
	}

	public static Long getElectronicCategory() {
		return electronicCategory;
	}

	public static Long getPhysicalCategory() {
		return physicalCategory;
	}



}
