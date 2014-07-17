package com.hbgz.pub.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QryPropertiesConfig 
{
	private static final Log log = LogFactory.getLog(QryPropertiesConfig.class);
	private static String configDIR = System.getProperty("user.dir");
	private static String separator = System.getProperty("file.separator");

	public static String getPropertyById(String id) throws Exception 
	{
		String configFile = configDIR + separator + "config.properties";
		PropertiesConfiguration cfg = new PropertiesConfiguration(configFile);
		cfg.setReloadingStrategy(new FileChangedReloadingStrategy());
		String val = cfg.getString(id);
		log.info("get properties by name:" + id + " value:" + val);
		return val;
	}
}
