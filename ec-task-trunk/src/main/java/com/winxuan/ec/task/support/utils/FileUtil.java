package com.winxuan.ec.task.support.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * luosh
 */
public class FileUtil {

	/**
	 * 文件拷贝
	 * @param sourceFilePath 源文件路径
	 * @param newFilePath 新文件路径
	 * @param isDeleteSourceFile 是否删除源文件
	 * @return 
	 * @throws IOException
	 */
	public static void copyFile(String sourceFilePath,String newFilePath,boolean isDeleteSourceFile) throws IOException{
		File sourceFile = new File(sourceFilePath);
		if(!sourceFile.exists()){
			return;
		}
		File newFile = new File(newFilePath.substring(0, newFilePath.lastIndexOf("/")>0?newFilePath.
				lastIndexOf("/"):newFilePath.lastIndexOf("\\")));
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		FileInputStream fileInputStream =null;
		FileOutputStream fileOutputStream =null;
		try{
			fileInputStream = new FileInputStream(sourceFile);
			fileOutputStream = new FileOutputStream(newFilePath);
			byte[] temp = new byte[2048];
			int readIndex = 0;
			while((readIndex = fileInputStream.read(temp))!=-1){
				fileOutputStream.write(temp, 0, readIndex);
				fileOutputStream.flush();
			}
		}finally{
			if(fileInputStream!=null){
				fileInputStream.close();
			}
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		if(isDeleteSourceFile){
			sourceFile.delete();
		}
	}
	/**
	 * 文件夹拷贝
	 * @param sourceFolderPath 源文件夹目录
	 * @param newFolderParentPath 新文件夹父目录
	 * @return
	 * @throws IOException 
	 */
	public static void copyFolder(String sourceFolderPath,String newFolderParentPath,boolean isDeleteSourceFolder) throws IOException{
		File sourceFolder = new File(sourceFolderPath);
		if(!sourceFolder.exists()){
			return;
		}
		File newFolder = new File(newFolderParentPath+"/"+sourceFolder.getName());
		if(!newFolder.exists()){
			newFolder.mkdirs();
		}
		for(File childFile:sourceFolder.listFiles()){
			if(childFile.isFile()){
				copyFile(childFile.getPath(),newFolderParentPath+"/"
					+sourceFolder.getName()+"/"+childFile.getName(),isDeleteSourceFolder);
			}
			else{
				copyFolder(childFile.getPath(),newFolderParentPath+"/"
						+sourceFolder.getName(),isDeleteSourceFolder);
			}
		}
		if(isDeleteSourceFolder){
			sourceFolder.delete();
		}
	}
	
	
	/**
	 * 获取文件字节数
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int getFileSize(String filePath) throws FileNotFoundException,IOException{
		FileInputStream fileInputStream =null;
		try{
			fileInputStream = new FileInputStream(filePath);
			return fileInputStream.available();
		}finally{
			if(fileInputStream!=null){
				fileInputStream.close();
			}
		}
	}
	/**
	 * 比较两个文件
	 * @param filePath
	 * @param ofilePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean compareFileSize(String filePath,String ofilePath) throws FileNotFoundException, IOException{
		return getFileSize(filePath)==getFileSize(ofilePath);
	}
	
	public static boolean delete(File deleteFolder){
		if(deleteFolder.isDirectory()){
			for(File file:deleteFolder.listFiles()){
				if (! delete(file)){
					return false;
				}
			}
			return deleteFolder.delete();
		}
		if(deleteFolder.isFile()){
			return deleteFolder.delete();
		}
		return true;
	}
}
