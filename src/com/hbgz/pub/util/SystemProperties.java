package com.hbgz.pub.util;

import java.io.File;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.pub.preferences.VariableExpander;
import com.hbgz.pub.preferences.VariableStore;

public class SystemProperties implements VariableStore
{
	private static SystemProperties sp = new SystemProperties();
	private static Log log = LogFactory.getLog(SystemProperties.class);
	private VariableExpander EXPANDER;

	public SystemProperties()
	{
		this.EXPANDER = new VariableExpander(this, "${", "}");
	}

	public static Properties getSystemProperties()
	{
		String file = SystemProperties.class.getClassLoader().getResource("system.properties").toString();

		file = file.replaceFirst("file:/", "");

		return FileUtils.readProperties(file);
	}

	public static String checkBackupDir()
	{
		String setupDir = getSetupDir();
		String dir = getBackupDir();
		String backupDir = setupDir + dir;
		if (!(FileUtils.exists(backupDir)))
		{
			FileUtils.createFolders(backupDir);
		}

		return backupDir;
	}

	public static String getSetupDir()
	{
		return sp.getVariableValue("setup.dir");
	}

	public static String getToolDir()
	{
		return sp.getVariableValue("database.tools.dir");
	}

	public static String getBackupDir()
	{
		return sp.getVariableValue("database.backup.dir");
	}

	public static void deleteBOM(String filename)
	{
		File file = new File(filename);
		if (file.exists())
			DeleteBOMSign.deleteBOM(file);
	}

	public static String getDbBackupCommand()
	{
		return sp.getVariableValue("database.backup.command");
	}

	public static String getDbRestoreCommand()
	{
		return sp.getVariableValue("database.restore.command");
	}

	public String getVariableValue(String variableName)
	{
		String preExpansion = getSystemProperties().getProperty(variableName);

		if (preExpansion == null)
		{
			return null;
		}

		return this.EXPANDER.expandVariables(preExpansion);
	}

	public static String getLogDir()
	{

		return sp.getVariableValue("logs.dir");
	}

	public static String getProjectName()
	{
		return sp.getVariableValue("project.name");
	}

	public static String getUpgradeDownloadDir()
	{
		return sp.getVariableValue("upgrade.download.dir");
	}
	
	public static String getHttpUrl()
	{
		return sp.getVariableValue("system.http");
	}

	public static String getUpgradeDeployUrlPrefix()
	{
		return sp.getVariableValue("upgrade.deploy.url.prefix");
	}

	public static String getUploadDir()
	{
		return sp.getVariableValue("system.upload.dir");
	}

	public static String getUploadImgWeb()
	{
		return sp.getVariableValue("imgweb.dir");
	}
	
	public static String getDomain()
	{
		return sp.getVariableValue("system.domain");
	}

	public static String getEncryptKey()
	{

		return sp.getVariableValue("system.security.seed");
	}
}
