package com.hiersun.oohdear.service;

import org.springframework.web.multipart.MultipartFile;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月14日 下午1:51:53 
 * @version 1.0 
 */
public interface FileService {

	public String upload(MultipartFile file);

	public String upload(MultipartFile file, String fileName, String path);
}
