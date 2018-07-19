package cn.qiniu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils extends org.apache.commons.io.FileUtils {

	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * 文件流读入
	 * 
	 * @param filePath
	 * @return
	 */
	public static FileInputStream readFile(String filePath) {
		File file = new File(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return fis;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void delete(String filePath) {
		if (filePath == null) {
			throw new RuntimeException("filePath attribute is required");
		}
		File file = new File(filePath);

		delete(file);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void delete(File file) {
		if (file == null) {
			throw new RuntimeException("file attribute is required");
		}
		if ((!file.exists()) || (!file.isFile())) {
			return;
		}
		file.delete();
	}

	/**
	 * 删除多个文件
	 * 
	 * @param filePath
	 */
	public static void deletes(String filePath) {
		File file = new File(filePath);
		File[] fileList = file.listFiles();
		for (File f : fileList) {
			f.delete();
		}
	}

	/**
	 * 文件是否存在check
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isExist(String filePath) {
		if (filePath == null) {
			throw new RuntimeException("filePath attribute is required");
		}
		File file = new File(filePath);

		return (file.exists()) && (file.isFile());
	}

	/**
	 * 文件夹是否存在check
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFolderExist(String filePath) {
		if (filePath == null) {
			throw new RuntimeException("filePath attribute is required");
		}
		File file = new File(filePath);
		return isFolderExist(file);
	}

	/**
	 * 文件夹是否存在check
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isFolderExist(File file) {
		if (file == null) {
			throw new RuntimeException("dir attribute is required");
		}
		return file.exists() && file.isDirectory();

	}

	public static File createFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 文件保存
	 * 
	 * @param stream
	 * @param filepath
	 * @param encodeType
	 */
	public static void save(InputStream stream, String filepath,
			String encodeType) {
		BufferedReader bufReader = null;
		try {
			bufReader = new BufferedReader(new InputStreamReader(stream,
					encodeType));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		BufferedWriter bufWriter = null;
		try {
			FileOutputStream os = new FileOutputStream(filepath, true);
			OutputStreamWriter osw = new OutputStreamWriter(os, encodeType);
			bufWriter = new BufferedWriter(osw);

			int readChar = -1;
			while ((readChar = bufReader.read()) != -1)
				bufWriter.write(readChar);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
				if (bufWriter != null)
					bufWriter.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 文件复制
	 * 
	 * @param fromFilePath
	 *            源路径
	 * @param toFilePath
	 *            目标路径
	 * @return
	 */
	public static boolean copy(String fromFilePath, String toFilePath) {
		if (!isExist(fromFilePath)) {
			return false;
		}

		File in = new File(fromFilePath);
		File out = new File(toFilePath);

		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;
		try {
			sourceChannel = new FileInputStream(in).getChannel();
			destinationChannel = new FileOutputStream(out).getChannel();
		} catch (FileNotFoundException e) {
			try {
				if (sourceChannel != null) {
					sourceChannel.close();
				}
			} catch (IOException localIOException1) {
				throw new RuntimeException(e);
			}
			throw new RuntimeException(e);
		}

		try {
			sourceChannel.transferTo(0L, sourceChannel.size(),
					destinationChannel);

			sourceChannel.close();
			destinationChannel.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (sourceChannel != null) {
					sourceChannel.close();
				}
				if (destinationChannel != null)
					destinationChannel.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		return true;
	}

	/**
	 * 创建目录
	 * 
	 * @param filePaht
	 */
	public static void maDir(String filePaht) {
		File file = new File(filePaht);
		mkDir(file);
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 */
	public static void mkDir(File dir) {
		if (isFolderExist(dir)) {
			throw new RuntimeException(
					"Unable to create directory as a file already exists with that name: "
							+ dir.getAbsolutePath());
		}

		// 如果存在，不创建
		if (!dir.exists()) {
			boolean result = dir.mkdirs();
			if (!result) {
				String msg = "Directory " + dir.getAbsolutePath()
						+ " creation was not successful for an unknown reason";

				throw new RuntimeException(msg);
			}
		}
	}

	/**
	 * 获取文件(根据正则表达式检索)
	 * 
	 * @param dir
	 * @param pattern
	 * @return
	 */
	public static List<File> getFiles(File dir, String pattern) {
		return getFiles(dir, pattern, null);
	}

	/**
	 * 获取文件
	 * 
	 * @param dir
	 * @param pattern
	 * @return
	 */
	public static List<File> getFilesRecurse(File dir, String pattern) {
		return getFilesRecurse(dir, pattern, null);
	}

	/**
	 * 获取除某些文件外列表
	 * 
	 * @param dir
	 * @param pattern
	 * @param exclude
	 * @return
	 */
	public static List<File> getFiles(File dir, String pattern, File exclude) {
		return getFilesRecurse(dir, Pattern.compile(pattern), exclude, false,
				new ArrayList<File>());
	}

	/**
	 * 递归取得文件
	 * 
	 * @param dir
	 * @param pattern
	 * @param exclude
	 * @return
	 */
	public static List<File> getFilesRecurse(File dir, String pattern,
			File exclude) {
		return getFilesRecurse(dir, Pattern.compile(pattern), exclude, true,
				new ArrayList<File>());
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static List<String> readLines(File file) throws IOException {
		if (!file.exists()) {
			return new ArrayList<String>();
		}
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> results = new ArrayList<String>();
		try {
			String line = reader.readLine();
			while (line != null) {
				results.add(line);
				line = reader.readLine();
			}
		} 
		finally {
				reader.close();
		}
		return results;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param dir
	 */
	public static void removeDir(File dir) {
		String[] list = dir.list();
		if (list == null) {
			list = new String[0];
		}
		for (int i = 0; i < list.length; i++) {
			String s = list[i];
			File f = new File(dir, s);
			if (f.isDirectory())
				removeDir(f);
			else {
				delete(f);
			}
		}
		// 删除文件夹
		dir.delete();
	}

	private static List<File> getFilesRecurse(File dir, Pattern pattern,
			File exclude, boolean rec, List<File> fileList) {
		for (File file : dir.listFiles()) {
			if (file.equals(exclude)) {
				continue;
			}
			if ((file.isDirectory()) && (rec)) {
				getFilesRecurse(file, pattern, exclude, rec, fileList);
			} else {
				Matcher m = pattern.matcher(file.getName());
				if (m.matches()) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
	
	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * 写入文件
	 * @param file 要写入的文件
	 */
	public static void writeToFile(String fileName, String content, boolean append) {
		try {
			FileUtils.write(new File(fileName), content, "utf-8", append);
			log.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
		}
	}

	/**
	 * 写入文件
	 * @param file 要写入的文件
	 */
	public static void writeToFile(String fileName, String content, String encoding, boolean append) {
		try {
			FileUtils.write(new File(fileName), content, encoding, append);
			log.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
		}
	}
}
