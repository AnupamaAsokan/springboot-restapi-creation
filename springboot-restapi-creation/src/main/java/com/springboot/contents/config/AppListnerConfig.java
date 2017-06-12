package com.springboot.contents.config;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import com.springboot.contents.util.FileConstants;

/**
 * Config class to delete the files at the startup
 * @author anupama
 *
 */
@Component
public class AppListnerConfig implements ApplicationListener<ApplicationReadyEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppListnerConfig.class);

	/**
	 * This event is to indicate that the application is ready to service requests.
	 * 
	 */
	
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		LOGGER.debug("springboot app is ready");
		FileSystemUtils.deleteRecursively(new File(FileConstants.DIRECTORY));
	}
}