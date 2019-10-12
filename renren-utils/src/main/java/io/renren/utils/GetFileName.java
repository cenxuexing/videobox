package io.renren.utils;

import java.io.File;

public class GetFileName {

	public static void main(String[] args) {
		String path = "F:\\code\\svn\\Global\\RockyMobi\\1_code\\h5game\\2019-01-29\\";
		GetFileName.traverseFolder2(path);
	}

	public static void traverseFolder2(String path) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
//						System.out.println("文件夹:" + file2.getAbsolutePath());
						System.out.println("http://uacms.stayrocky.com/wapgame/" + file2.getName());
						//traverseFolder2(file2.getAbsolutePath());
					}
//					if (!file2.isDirectory()) {
//						System.out.println("文件:" + file2.getAbsolutePath());
//					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}
}
