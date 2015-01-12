package com.hbgz.pub.file;

import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.SystemProperties;
import com.tools.pub.utils.StringUtil;

public class FileBiz
{

	public static String getUploadDir(String levelDir)
	{
		StringBuffer dirBuffer = new StringBuffer("");
		//dirBuffer.append(SystemProperties.getUploadDir());
		if ((!(StringUtil.isEmpty(levelDir))) && (!("/".equals(levelDir))))
		{
			dirBuffer.append(levelDir);
			if (!(levelDir.substring(levelDir.length() - 1).equals("/")))
			{
				dirBuffer.append("/");
			}
		}
		String dateTime = DateUtils.getORADateTime();
		dirBuffer.append(dateTime.substring(0, 6)).append("/").append(StringUtil.getRands(1, "l")).append("/");

		return dirBuffer.toString();
	}

	public static String getFullPath(String filePath)
	{
		if (StringUtil.isEmpty(filePath))
		{
			return null;
		}
		StringBuffer path = new StringBuffer("");

		path.append(SystemProperties.getUploadImgWeb());

		path.append(SystemProperties.getUploadDir());

		//path.append(SystemProperties.getProjectName()).append("/");
		return filePath.replace(SystemProperties.getUploadDir(), path);
	}
}
