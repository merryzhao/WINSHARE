package com.winxuan.ec.support.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-30
 */
public class ExceptionUtil {
	private static final int MAGIC_ZERO = 0;
	private static final int MAGIC_30 = 30;
	private static final int MAGIC_50 = 50;
	private static final int MAGIC_100 = 100;
	private static final int MAGIC_500 = 500;

	public static com.winxuan.ec.model.exception.ExceptionLog newExceptionLog(HttpServletRequest request) {
		com.winxuan.ec.model.exception.ExceptionLog exceptionLog = new com.winxuan.ec.model.exception.ExceptionLog();
		String str = request.getHeader("host");
		if (str != null) {
			exceptionLog.setHost(str.length() > MAGIC_50 ? str.substring(
					MAGIC_ZERO, MAGIC_50) : str);
		}

		str = request.getHeader("user-agent");
		if (str != null) {
			exceptionLog.setUserAgent(str.length() > MAGIC_100 ? str.substring(
					MAGIC_ZERO, MAGIC_100) : str);
		}

		str = request.getHeader("accept");
		if (str != null) {
			exceptionLog.setAccept(str.length() > MAGIC_100 ? str.substring(
					MAGIC_ZERO, MAGIC_100) : str);
		}

		str = request.getHeader("accept-language");
		if (str != null) {
			exceptionLog.setLanguage(str.length() > MAGIC_50 ? str.substring(
					MAGIC_ZERO, MAGIC_50) : str);
		}

		str = request.getHeader("accept-charset");
		if (str != null) {
			exceptionLog.setCharset(str.length() > MAGIC_50 ? str.substring(
					MAGIC_ZERO, MAGIC_50) : str);
		}

		str = request.getHeader("cookie");
		if (str != null) {
			exceptionLog.setCookie(str.length() > MAGIC_500 ? str.substring(
					MAGIC_ZERO, MAGIC_500) : str);
		}

		str = request.getHeader("if-modified-since");
		if (str != null) {
			exceptionLog.setModified(str.length() > MAGIC_30 ? str.substring(
					MAGIC_ZERO, MAGIC_30) : str);
		}

		exceptionLog.setMethod(request.getMethod());
		exceptionLog.setRequestUrl(request.getRequestURL() == null ? ""
				: request.getRequestURL().toString());
		exceptionLog.setIp(com.winxuan.ec.support.util.IpUtils
				.getClientIP(request));
		return exceptionLog;
	}
}
