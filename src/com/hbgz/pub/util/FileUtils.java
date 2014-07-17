package com.hbgz.pub.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.pub.log.Logger;

public class FileUtils

{
	private static Log log = LogFactory.getLog(FileUtils.class);

	private static String _extension;

	public static final boolean exists(String path)
	{
		if ((path == null) || ("".equals(path)))
		{
			return false;
		}
		path = decode(path);
		File file = new File(path);
		return file.exists();
	}

	public static final long create(String filePath, String content)
	{
		return create(filePath, content, "UTF-8");
	}

	public static final long create(String filePath, String content, String encoding)
	{
		File file = new File(filePath);
		if (!(file.exists()))
		{
			try
			{
				String folderPath = file.getParent();
				if (!(createFolders(folderPath)))
				{
					return -1L;
				}
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
				return -1L;
			}
		}
		try
		{
			PrintWriter myFile = new PrintWriter(file, encoding);
			myFile.println(content);
			myFile.close();
			return file.length();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1L;
	}

	public static final long create(String filePath, String fileName, InputStream stream)
	{
		if ((stream == null) || (filePath == null) || ("".equals(filePath)) || (fileName == null) || ("".equals(fileName)))
		{
			return -1L;
		}
		int index = filePath.lastIndexOf("\\");
		if (index == -1)
		{
			index = filePath.lastIndexOf("/");
		}
		if (index == -1)
		{
			filePath = filePath + "/";
		}
		return create(filePath + fileName, stream);
	}

	public static final long create(String fileName, InputStream stream)
	{
		long result = -1L;
		if ((stream == null) || (fileName == null) || ("".equals(fileName)))
		{
			return result;
		}

		File saveFile = new File(fileName);
		if (saveFile.exists())
		{
			saveFile.delete();
		} 
	
		try
		{
			OutputStream out = new FileOutputStream(saveFile);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1)
			{
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			stream.close();
			result = saveFile.length();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static final String readText(String file)
	{
		return readText(file, "UTF-8");
	}

	public static final String readText(String file, String encoding)
	{
		file = decode(file);
		if (!(exists(file)))
		{
			log.error("File [" + file + "] is not exists.");
			return null;
		}
		if ((encoding == null) || ("".endsWith(encoding)))
		{
			encoding = "UTF-8";
		}
		String st = "";
		try
		{
			FileInputStream fs = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fs, encoding);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer str = new StringBuffer("");
			try
			{
				String data = "";
				while ((data = br.readLine()) != null)
					str.append(data + "\r\n");
			} catch (Exception e)
			{
				str.append(e.toString());
			}
			br.close();
			isr.close();
			fs.close();
			st = str.toString();
		} catch (IOException es)
		{
			st = "";
		}
		return st;
	}

	public static final FileInputStream readStream(String file)
	{
		file = decode(file);
		//System.out.println("readStream:" + file);
		//System.out.println("readStream--exists(file):" + exists(file));
		if (!(exists(file)))
		{
			return null;
		}
		FileInputStream fs = null;
		try
		{

			fs = new FileInputStream(file);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return fs;
	}

	public static final Properties readProperties(String file)
	{
		file = decode(file);
		Properties properties = null;
		try
		{
			FileInputStream stream = readStream(file);
			if (stream == null)
				return null;
			properties = new Properties();
			properties.load(stream);
			stream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return properties;
	}

	public static final byte[] readByte(String file)
	{
		file = decode(file);
		if (!(exists(file)))
			return null;
		try
		{
			FileInputStream fs = new FileInputStream(file);
			byte[] buffer = new byte[fs.available()];
			fs.read(buffer);
			fs.close();
			return buffer;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delFile(String filePath)
	{
		boolean bea = false;
		try
		{
			File myDelFile = new File(filePath);
			if (myDelFile.exists())
				bea = myDelFile.delete();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bea;
	}

	public static final boolean delAllFile(String path)
	{
		boolean bea = false;
		File file = new File(path);
		if (!(file.exists()))
		{
			return bea;
		}
		if (!(file.isDirectory()))
		{
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; ++i)
		{
			String tmpPath = path + File.separator + tempList[i];

			temp = new File(tmpPath);
			if (temp.isDirectory())
			{
				delFolder(tmpPath);
			}
			temp.delete();
		}
		return true;
	}

	public static final long getFileSize(String filepath)
	{
		if ((filepath == null) || ("".equals(filepath)))
			return -1L;
		File f = new File(filepath);
		if (f.exists())
		{
			return f.length();
		}
		return -1L;
	}

	public static final boolean delFolder(String folderPath)
	{
		try
		{
			File folder = new File(folderPath);
			if (!(folder.exists()))
				return false;
			if (folder.isDirectory())
			{
				delAllFile(folderPath);
			}
			folder.delete();
			return true;
		} catch (Exception e)
		{
		}
		return false;
	}

	public static final boolean createFolders(String folderPath)
	{
		if ((folderPath == null) || ("".equals(folderPath)))
		{
			return false;
		}
		String newFolderPath = folderPath.replaceAll("\\\\", "/").replaceAll("//", "/");

		String[] arrPath = newFolderPath.split("/");
		StringBuffer sbPath = new StringBuffer("");
		for (int i = 0; i < arrPath.length; ++i)
		{
			String path = arrPath[i];
			if (path == null)
				continue;
			if ("".equals(path))
			{
				continue;
			}
			if (sbPath.length() > 0)
			{
				sbPath.append("/");
			}
			sbPath.append(path.trim());
			System.out.println("sbPath.toString():"+sbPath.toString());
			boolean su = createFolder(sbPath.toString());

			if (!(su))
			{
				System.out.println(sbPath);
				return false;
			}
		}
		return true;
	}

	public static final boolean createFolder(String folderPath)
	{
//		if ((folderPath == null) || ("".equals(folderPath)) || ("/".equals(folderPath)) || (":".equals(folderPath)))
//		{
//			return false;
//		}
		try
		{
			System.out.println("folderPath:"+folderPath);
			File folder = new File(folderPath);
			System.out.println("folder.exists():"+folder.exists());
			if (!(folder.exists()))
			{
				folder.mkdirs();
			}
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static final String decode(String file)
	{
		try
		{
			return URLDecoder.decode(file, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return file;
	}

	public static String processFilePathSeperator(String str)
	{
		return ((str == null) ? "" : str.replaceAll("[|]", "/").replaceAll("\\\\", "/").replaceAll("//", "/"));
	}

	public static void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception
	{
		try
		{
			File f = new File(filePath);
			if (!(f.exists()))
			{
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));

			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			String downFileName = new String(f.getName().getBytes(), "iso-8859-1");

			log.debug("Download filepath = " + filePath);
			if (isOnLine)
			{
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; filename=" + downFileName);
			} else
			{
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + downFileName);
			}

			OutputStream out = response.getOutputStream();
			try
			{
				while ((len = br.read(buf)) > 0)
					out.write(buf, 0, len);
			} catch (Exception e)
			{
			}
			br.close();
			out.close();
		} catch (Exception e)
		{
			throw e;
		}
	}

	public static void copyFolder(String path, String des)
	{
		try
		{
			if (!(new File(des).exists()))
			{
				new File(des).mkdirs();
			}
			File a = new File(path);
			if (a.exists())
			{
				String[] file = a.list();
				File temp = null;
				for (int i = 0; (file != null) && (i < file.length); ++i)
				{
					if (path.endsWith(File.separator))
						temp = new File(path + file[i]);
					else
					{
						temp = new File(path + File.separator + file[i]);
					}
					if (temp.isFile())
					{
						FileInputStream input = new FileInputStream(temp);
						create(des + "/" + temp.getName().toString(), input);
					}
					if (temp.isDirectory())
						copyFolder(path + "/" + file[i], des + "/" + file[i]);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getClassPathResourceUri(String file)
	{
		return FileUtils.class.getResource(file).toString().replaceFirst("file:/", "");
	}

	private static FilenameFilter getFileExtensionFilter(String extension)
	{
		_extension = extension;
		return new FilenameFilter()
		{
			public boolean accept(File file, String name)
			{
				boolean ret = (file.isDirectory()) && (name.endsWith(_extension));
				return ret;
			}
		};
	}

	public static File[] listFileByExtension(String dirPath, String extension)
	{
		if (!(exists(dirPath)))
		{
			Logger.exception(new Exception("指定的目录【" + dirPath + "】不存在。"));
		}

		File dir = new File(dirPath);
		if (!(dir.isDirectory()))
		{
			Logger.exception(new Exception("指定的目录【" + dirPath + "】不是目录。"));
		}

		return dir.listFiles(getFileExtensionFilter(extension));
	}

	private static FilenameFilter getFileRegexFilter(String regex)
	{
		_extension = regex;
		return new FilenameFilter()
		{
			public boolean accept(File file, String name)
			{
				boolean ret = (file.isDirectory()) && (name.matches(_extension));
				return ret;
			}
		};
	}

	public static File[] listFileByRegex(String dirPath, String regex) throws Exception
	{
		if (!(exists(dirPath)))
		{
			throw new Exception("指定的目录【" + dirPath + "】不存在。");
		}

		File dir = new File(dirPath);
		if (!(dir.isDirectory()))
		{
			throw new Exception("指定的目录【" + dirPath + "】不是目录。");
		}

		return dir.listFiles(getFileRegexFilter(regex));
	}

	public static File[] listFile(String dirPath) throws Exception
	{
		if (!(exists(dirPath)))
		{
			throw new Exception("指定的目录【" + dirPath + "】不存在。");
		}

		File dir = new File(dirPath);
		if (!(dir.isDirectory()))
		{
			throw new Exception("指定的目录【" + dirPath + "】不是目录。");
		}

		return dir.listFiles();
	}

	public static String getModifiedTime(File file)
	{
		Calendar cal = Calendar.getInstance();
		long time = file.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.setTimeInMillis(time);
		return formatter.format(cal.getTime());
	}

	public static String formatFileSize(long size)
	{
		String fileSize = " KB";
		NumberFormat nf = NumberFormat.getInstance();
		fileSize = nf.format((size / 1024L == 0L) ? 1L : size / 1024L) + fileSize;
		return fileSize;
	}

	public static File[] sortByLastModified(File[] fileArrays)
	{
		Arrays.sort(fileArrays, new CompratorByLastModified());
		return fileArrays;
	}

	public static void appendContent(String fileName, String content)
	{
		appendContent(fileName, content, "");
	}

	public static void appendContentByLog(String fileName, String content)
	{
		appendContent(fileName, content, "X9 log Starting...\n");
	}

	public static void appendContent(String fileName, String content, String defaultCotent)
	{
		appendContent(fileName, content, defaultCotent, "UTF-8");
	}

	public static void appendContent(String fileName, String content, String defaultCotent, String encoding)
	{
		try
		{
			if (!(exists(fileName)))
			{
				create(fileName, defaultCotent, encoding);
			}

			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static class CompratorByLastModified implements Comparator<File>
	{
		public int compare(File f1, File f2)
		{
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0L)
				return 1;
			if (diff == 0L)
			{
				return 0;
			}
			return -1;
		}

		public boolean equals(Object obj)
		{
			return true;
		}
	}
}
