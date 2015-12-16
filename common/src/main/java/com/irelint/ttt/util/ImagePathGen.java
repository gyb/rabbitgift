package com.irelint.ttt.util;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;

@Lazy(false)
@Component
public class ImagePathGen implements ServletConfigAware {
	private final static Logger logger = LoggerFactory.getLogger(ImagePathGen.class);
	private String basePath;
	private static final String baseUrl = "/resources/upload/";
	private String uploadPath;
	private String uploadUrl;
	private AtomicLong next = new AtomicLong(System.currentTimeMillis() % (60 * 60 * 1000));
	
	@Override
	public void setServletConfig(ServletConfig config) {
		basePath = config.getServletContext().getRealPath("/") + "resources/upload/";
		createUploadPath();
	}

	public void createUploadPath() {
		Calendar cal = Calendar.getInstance();
		String path = cal.get(Calendar.YEAR) + ""
			+ cal.get(Calendar.MONTH) + "/"
			+ cal.get(Calendar.DAY_OF_MONTH) + "/"
			+ cal.get(Calendar.HOUR_OF_DAY) + "/";
		
		logger.info("Create uploaded image directory:" + path);
		File file = new File(basePath + path);
		file.mkdirs();
		
		synchronized (this) {
			uploadPath = basePath + path;
			uploadUrl = baseUrl + path;
		}
	}
	
	public String getUploadFilePath() {
		return uploadPath;
	}
	
	public String getImageUrl() {
		return uploadUrl;
	}
	
	public long getNextId() {
		return next.incrementAndGet();
	}
}

