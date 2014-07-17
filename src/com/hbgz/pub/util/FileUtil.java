package com.hbgz.pub.util;

import java.util.ArrayList;
import java.util.List;

public class FileUtil 
{
	private static List<String> picTypeList;
	
	static
	{
		picTypeList = new ArrayList<String>();
		picTypeList.add("jpg");
		picTypeList.add("png");
		picTypeList.add("tif");
		picTypeList.add("bmp");
		picTypeList.add("jpeg");
	}
	//判断是否是图片格式
	public boolean isPicFile(String fileName) throws Exception
	{
		boolean flag = false;
		if(fileName.lastIndexOf(".") != -1)
		{
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			for (int i = 0, len = picTypeList.size(); i < len; i++) 
			{
				String picType = picTypeList.get(i);
				if(suffix.equals(picType))
				{
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
