package com.winxuan.ec.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * IO工具 ,常用IO操作
 * @author Heyadong
 * @version 1.0 , 2012-1-16
 */
public class IOUtils {
	/**
	 * 关闭 INPUT OUTPUT 流,忽略异常
	 * @param ios
	 */
	public static void close(Closeable ... ios){
		if (ios != null && ios.length > 0){
			for (Closeable c : ios){
				try {
					if (c != null){
						c.close();
					}
				} catch (IOException e) {
					continue;
				}
			}
		}
	}
	/**
	 * 关闭socket 忽略异常
	 * @param s
	 */
	public static void closeSocket(Socket s){
		if (s != null){
			try {
				s.close();
			} catch (IOException e) {
				return;
			}
		}
	}
	
	/**
	 * 将input流读入 output流
	 * 直到input.read 返回 -1为止
	 * 不关闭流
	 * @param source
	 * @param dest
	 */
	public static void writeStream(InputStream src, OutputStream dest) throws IOException{
		byte[] data = new byte[MagicNumber.BYTE_256];
		int len = 0;
		while (-1 != (len = src.read(data))){
			dest.write(data, 0, len);
		}
	}
}
