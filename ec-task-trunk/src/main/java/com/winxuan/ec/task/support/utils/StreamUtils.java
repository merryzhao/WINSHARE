package com.winxuan.ec.task.support.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 流处理.工具
 * @author Heyadong
 * @date 2011-11-30
 */
public class StreamUtils {
	/**
	 * 关闭IO流,忽略异常
	 * @param streams
	 */
	public static void close(Closeable ... streams) {
		if ( streams != null && streams.length > 0) {
			for (Closeable c : streams) {
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将 in中的流,读写到out中去.读完为止
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void write(InputStream in , OutputStream out) throws IOException{
		int len = 0;
		byte[] buff = new byte[512]; 
		while (-1 != (len = in.read(buff))){
			out.write(buff,0,len);
		}
	}
}
