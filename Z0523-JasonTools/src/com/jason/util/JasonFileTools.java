package com.jason.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Jason的文件工具
 * @author Jason
 *
 */
public class JasonFileTools {
	
	private JasonFileTools() {
		
	}

	static String defaultPath = "d://test/default/";
	static String defaultName = "default.txt";

	/**
	 * 文件持久化 对象 默认目录
	 * 
	 * @param obj
	 */
	public static void saveObjectToFile(Serializable save) {
		saveObjectToFile(save, defaultName);
	}

	/**
	 * 文件持久化 对象 默认文件
	 * 
	 * @param obj
	 */
	public static void saveObjectToFile(Serializable save, String fileName) {
		if(fileName.contains("/")) {
			new Exception("文件名不能有/").printStackTrace();
			return;
		}
		saveObjectToFile(save, defaultPath, fileName);
	}
	
	/**
	 * 文件持久化 对象
	 * @param obj
	 */
	public static void saveObjectToFile(Serializable save, String path, String fileName) {
		System.out.println("Save File:");
		try {
			File fSave = null;
			if (path.endsWith("/")) {
				fSave = fileExistCheck(path + fileName);
			} else {
				fSave = fileExistCheck(path + "/" + fileName);
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fSave));
			oos.writeObject(save);
			oos.close();
			System.out.println("Save File Success. " + " Size = " + getFileSize(fSave) + " " + fSave.getAbsolutePath());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件大小字符串
	 * @param f
	 * @return
	 */
	private static String getFileSize(File f) {
		String s = "";
		long fileSize = f.length();
		if (fileSize >= 1000000) {
			s = fileSize / 1000000 + "M";
		}else{
			s = fileSize / 1000 + "K";
		}
		return s;
	}

	/**
	 * 建目录，重命名
	 * 文件存在检测，重复的话就重命名
	 * @param filePath
	 * @return
	 */
	private static File fileExistCheck(String filePath) {
		String info = "";

		File file = new File(filePath);
		String fileName = file.getName();
		File path = new File(file.getAbsolutePath());
		path = new File(file.getAbsolutePath()).getParentFile();

		if(fileName.equals("")) {
			info += " 文件名为空 -> defult.object .";
			fileName = "defult.object";
		}
		


		if(!path.exists()) {
			boolean mk = path.mkdirs();
		}
		if (file.exists()) {//文件已存在

			String fileNameFont = null;
			String fileNameback = null;

			int dotIndex = fileName.lastIndexOf(".");
			if(dotIndex == -1) {//没后缀
				fileNameFont = fileName;
				fileNameback = "";
			}else {
				fileNameFont = fileName.substring(0, dotIndex);
				fileNameback = fileName.substring(dotIndex, fileName.length());
			}
			
			for (int i = 1;; i++) {
				if(i >= 1000) {
					break;
				}
				String Num = "";
				if (i <= 9) {
					Num = "00" + i;
				} else if (i <= 99) {
					Num = "0" + i;
				} else {
					Num = "" + i;
				}
				File newFile = new File(path.getAbsolutePath() + "/" + fileNameFont + "-" + Num + fileNameback);
				if (!newFile.exists()) {
					file.renameTo(newFile);
					info += " 文件已存在 : " + file.getName() + "  ,把旧文件重命名为 : "
							+ newFile.getName();
					break;
				}
			}

		}
		
		if(!"".equals(info)) {
			System.err.println(info);
		}
		
		return file;
	}
	
	/**
	 * 从文件恢复对象
	 * @param filePath 文件目录
	 * @param fileName 文件名
	 * @return 对象
	 */
	public static Object loadObjectInfile(String filePath, String fileName) {
		Object obj = null;
		try {
			File f = null;
			if (filePath.endsWith("/")) {
				f = new File(filePath + fileName);
			} else {
				f = new File(filePath + "/" + fileName);
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			obj = ois.readObject();
			ois.close();
			System.out.println("对象恢复成功。" + f.getAbsolutePath() + ",File Size = " + getFileSize(f));
		} catch (Exception e) {//对象恢复失败
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * 从文件恢复对象
	 * @return
	 */
	public static Object loadObjectInfile() {
		return loadObjectInfile(defaultPath, defaultName);
	}

	/**
	 * 从文件恢复对象
	 * @param fileName 文件名
	 * @return
	 */
	public static Object loadObjectInfile(String fileName) {
		return loadObjectInfile(defaultPath, fileName);
	}
	
	/**
	 * 获取文件的CRC32值
	 * @param f
	 * @param size
	 * @return
	 */
	public static long fileCrc(File f , int size) {

		try {
			InputStream is = new FileInputStream(f);
			byte[] bs = new byte[size];
			is.read(bs);
			is.close();
			CRC32 cr = new CRC32();
			cr.update(bs);
			return cr.getValue();

		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 保存byte到文件
	 * @param bs
	 * @param f
	 */
	public static void saveByteToFile(byte[] bs,File f) {
		try {
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bs);
		fos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
