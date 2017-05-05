package com.hiersun.oohdear.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hiersun.oohdear.service.FileService;
import com.hiersun.oohdear.util.RandomUtils;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月14日 下午1:53:21 
 * @version 1.0 
 */
@Service
public class FileServiceImpl implements FileService {
	@Value("${user.server.url}")
	private String serverUrl;

	@Value("${user.upload.path}")
	private String uploadPath;

	@Override
	public String upload(MultipartFile file) {
		String avatar = serverUrl;
		// 获取文件名
		String originalName = file.getOriginalFilename();
		//String files=originalName.substring(originalName.lastIndexOf("."),originalName.length());
		//String fileName=RandomUtils.generateString(15); //图片以随机数命名
		File dest = new File(uploadPath, originalName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			avatar += "/" + originalName;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			avatar = "";
		}
		return avatar;
	}

	@Override
	public String upload(MultipartFile file, String fileName, String path) {
		String avatar = serverUrl;
		// 获取文件名
		File dest = new File(path, fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			avatar += "/" + fileName;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			avatar = "";
		}
		return avatar;
	}
}
