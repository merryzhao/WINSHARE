package com.winxuan.ec.task.support.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Utils {
	public static String getMD5(InputStream inputStream) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = inputStream.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] b = md.digest();
			return byteToHexStringSingle(b);// byteToHexString(b);
			// 16位加密
			// return buf.toString().substring(8, 24);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				inputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String byteToHexStringSingle(byte[] byteArray) {
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
}
