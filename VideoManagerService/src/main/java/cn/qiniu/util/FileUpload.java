package cn.qiniu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import cn.qiniu.config.Global.TEMP_FILE_PATH;

@Component
public class FileUpload {
	/**
	 * statement: 执行文件上传
	 * @param file       文件内容
	 * @param fileName   文件名称
	 * @param folderName 文件路径
	 * @throws UnsupportedEncodingException 
	 */
	public String uploadPic(byte[] fileData, String extName) throws FileNotFoundException, UnsupportedEncodingException {
		//定义文件存放路径
		String filePath = "";
		//定义临时文件名
		String fileNm = CommonUtil.getUUID() + extName;
		
		try {
			//设置文件上传路径(默认为"/temp"文件夹)
			filePath = PathUtil.getClasspath() + TEMP_FILE_PATH.DEFAULT;
			//生成文件对象
			File file = new File(filePath, fileNm);
			
			//如果当前文件保存路径不存在，生成新文件路径
			if (!FileUtils.isFolderExist(filePath)) {
				//生成新文件路径
				FileUtils.maDir(filePath);
			}
			//如果当前需保存的文件不存在
			if (!file.exists()) {
				//创建新文件
				file.createNewFile();
			}
			//创建文件流
			FileOutputStream outputStream = new FileOutputStream(file);
			//通过文件流生成文件
			outputStream.write(fileData);
			outputStream.flush();
			outputStream.close();
			outputStream = null;
			//定义返回文件存放路径（文件夹名  + "/" + 文件名）
			return filePath + "/" + fileNm;
		} catch (FileNotFoundException e) {
			System.out.println(" File Not Found! ");
		} catch (IOException e) {
			System.out.println(" Create New File Failed! ");
		}
		return "";
	}
	
	/**
	 * statement: 删除WebService端的指定文件
	 * @param fileName   文件名称
	 * @param folderName 文件路径
	 */
	public boolean deleteFile(String filePath) {
		//生成文件对象
		File file = new File(filePath);
		try {
			//如果存在需删除文件，则删除文件
			if (file.exists()) {
				//文件删除
				file.delete();
			}
			return true;
		} catch (Exception e) {
			System.out.println(" Delete File Failed! ");
		}
		return false;
	}
}
