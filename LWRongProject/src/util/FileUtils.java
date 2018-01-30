package util;

import java.io.File;

public class  FileUtils{

	/**
	 * 遍历删除文件夹
	 * 
	 * @param dir
	 *            需要删除的文件夹
	 * @return boolean
	 */
	static boolean deleteDir(File dir) {
		if (!dir.exists() && !dir.isDirectory()) {
			return false;
		}
		if (dir.isDirectory()) {
			String[] children = dir.list();// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * @param file
	 *            递归获取文件夹大小
	 * 
	 * @return 文件夹大小
	 */
	public static long getFileSize(File file) {
		if (!file.exists() && !file.isDirectory()) {
			return -1;
		}
		long size = 0;
		File flist[] = file.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
}
