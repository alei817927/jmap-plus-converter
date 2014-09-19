package com.makenv.mapdc.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

	/**
	 * 创建一个目录
	 * 
	 * @param dir
	 * @exception RuntimeException
	 *                ,创建目录失败会抛出此异常
	 */
	public static void createDir(File dir) {
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException("Can't create the dir [" + dir + "]");
		}
	}

	public static void createDir(String dir) {
		createDir(new File(dir));
	}

	/**
	 * 删除一个文件或者目录
	 * 
	 * @param file
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] _files = file.listFiles();
			for (File _f : _files) {
				delete(_f);
			}
			file.delete();
		}
	}

	public static void delete(String file) {
		File _file = new File(file);
		if (_file.exists()) {
			delete(_file);
		}
	}

	/**
	 * 删除一个目录下的除exculde指定的后缀名外的所有子文件或子目录
	 * 
	 * @param file
	 */
	public static void cleanFolder(File file, String exculde) {
		if (!file.isDirectory())
			return;

		File[] _files = file.listFiles();
		for (File _f : _files) {
			if (_f.getName().endsWith(exculde))
				continue;
			delete(_f);
		}
	}

	/**
	 * 删除一个目录下的除exculde指定的后缀名外的所有子文件或子目录
	 * 
	 * @param file
	 */
	public static void cleanFolder(File file) {
		if (!file.isDirectory())
			return;

		File[] _files = file.listFiles();
		for (File _f : _files) {
			delete(_f);
		}
	}

	public static void cleanFolder(String file) {
		File _file = new File(file);
		if (_file.exists()) {
			cleanFolder(_file);
		} else {
			_file.mkdir();
		}
	}

	public static String readFile(String file) throws IOException {
		return readFile(file, "utf-8");
	}

	public static String readFile(String file, String encode) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode == null ? "utf-8" : encode));
			StringBuffer _result = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				_result.append(line.trim());
			}
			return _result.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static boolean writeFile(String targetFile, String contents) {
		BufferedWriter _output = null;
		try {
			_output = new BufferedWriter(new FileWriter(targetFile));
			_output.write(contents);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (_output != null) {
				try {
					_output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public static boolean isExist(String fileName) {
		File _file = new File(fileName);
		return _file.exists();
	}
}
