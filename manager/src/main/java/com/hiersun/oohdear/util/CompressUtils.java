package com.hiersun.oohdear.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class CompressUtils {

	private static final int BUFFER = 2048;

	/**
	 * 压缩source1和source2到destination
	 * @param source1 文件地址
	 * @param source2 输入流
	 * @param destination 输出流
	 * @throws IOException
	 */
	public static void zip(String source1, InputStream source2, OutputStream destination) throws IOException {
		ZipOutputStream out = new ZipOutputStream(destination, Charset.forName("UTF-8"));
		File file = new File(source1);
		String fileName = file.getName();
		if (!file.isDirectory()) {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			//压缩source1
			ZipEntry entry = new ZipEntry(fileName);
			out.putNextEntry(entry);
			copy(bis, out);
		}
		//压缩source2
		String source2Name = fileName.substring(0, fileName.indexOf(".")) + ".txt";
		if (source2Name.equals(fileName)) {
			source2Name = source2Name.replace(".txt", "(1).txt");
		}
		ZipEntry entry = new ZipEntry(source2Name);
		out.putNextEntry(entry);
		copy(source2, out);
		source2.close();
		out.closeEntry();
		out.close();
	}

	/**
	 * 复制输入流到输出流
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private static void copy(InputStream in, OutputStream out) throws IOException {
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = in.read(data, 0, BUFFER)) != -1) {
			out.write(data, 0, count);
		}
		in.close();
	}

}
