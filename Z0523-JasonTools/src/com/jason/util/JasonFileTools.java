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
 * Jason���ļ�����
 * @author Jason
 *
 */
public class JasonFileTools {
	
	private JasonFileTools() {
		
	}

	static String defaultPath = "d://test/default/";
	static String defaultName = "default.txt";

	/**
	 * �ļ��־û� ���� Ĭ��Ŀ¼
	 * 
	 * @param obj
	 */
	public static void saveObjectToFile(Serializable save) {
		saveObjectToFile(save, defaultName);
	}

	/**
	 * �ļ��־û� ���� Ĭ���ļ�
	 * 
	 * @param obj
	 */
	public static void saveObjectToFile(Serializable save, String fileName) {
		if(fileName.contains("/")) {
			new Exception("�ļ���������/").printStackTrace();
			return;
		}
		saveObjectToFile(save, defaultPath, fileName);
	}
	
	/**
	 * �ļ��־û� ����
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
	 * ��ȡ�ļ���С�ַ���
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
	 * ��Ŀ¼��������
	 * �ļ����ڼ�⣬�ظ��Ļ���������
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
			info += " �ļ���Ϊ�� -> defult.object .";
			fileName = "defult.object";
		}
		


		if(!path.exists()) {
			boolean mk = path.mkdirs();
		}
		if (file.exists()) {//�ļ��Ѵ���

			String fileNameFont = null;
			String fileNameback = null;

			int dotIndex = fileName.lastIndexOf(".");
			if(dotIndex == -1) {//û��׺
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
					info += " �ļ��Ѵ��� : " + file.getName() + "  ,�Ѿ��ļ�������Ϊ : "
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
	 * ���ļ��ָ�����
	 * @param filePath �ļ�Ŀ¼
	 * @param fileName �ļ���
	 * @return ����
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
			System.out.println("����ָ��ɹ���" + f.getAbsolutePath() + ",File Size = " + getFileSize(f));
		} catch (Exception e) {//����ָ�ʧ��
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * ���ļ��ָ�����
	 * @return
	 */
	public static Object loadObjectInfile() {
		return loadObjectInfile(defaultPath, defaultName);
	}

	/**
	 * ���ļ��ָ�����
	 * @param fileName �ļ���
	 * @return
	 */
	public static Object loadObjectInfile(String fileName) {
		return loadObjectInfile(defaultPath, fileName);
	}
	
	/**
	 * ��ȡ�ļ���CRC32ֵ
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
	 * ����byte���ļ�
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
