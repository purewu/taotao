package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.IPictureService;

@Service
public class PictureServiceImpl implements IPictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map map = new HashMap<>();
		try {
			String oldName = uploadFile.getOriginalFilename();
			String newName = IDUtils.genImageName();
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			String name = newName + oldName.substring(oldName.lastIndexOf("."));
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, name, uploadFile.getInputStream());

			if (!result) {
				map.put("error", 1);
				map.put("message", "文件上传失败");
			}
			map.put("error", 0);
			map.put("url", IMAGE_BASE_URL + imagePath + "/" + name);
			return map;
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "文件上传异常");
			return map;
		}

	}

}
