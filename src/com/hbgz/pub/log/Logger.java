 package com.hbgz.pub.log;
 
 import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbgz.pub.util.DateUtils;
import com.hbgz.pub.util.FileUtils;
import com.hbgz.pub.util.SystemProperties;
 
 public class Logger
 {
   private static Log logger = LogFactory.getLog(Logger.class);
   public static final String SYS_LEVEL = "SYS";
   public static final String APP_LEVEL = "APP";
   public static final String ERR_LEVEL = "ERR";
   public static final String ALL_LEVEL = "SYS,APP,ERR";
 
   private static String getCurrentTime()
   {
     return new DateUtils().format(DateUtils.CHN_DATE_TIME_EXTENDED_FORMAT); }
 
   private static String getCurrentDate() {
     return new DateUtils().format(DateUtils.ORA_DATE_FORMAT);
   }
 
   private static String getFile(String level) {
     String fileName = ((level == null) || (level.equals(""))) ? "ERR" : level;
 
     return SystemProperties.getLogDir() + "/" + fileName + "-" + getCurrentDate() + ".log"; }
 
   private static void writeLog(String file, String content) {
     logger.debug(content);
     FileUtils.appendContentByLog(file, content); }
 
   private static String getLoginfo(String type, String man, String logInfo) {
     return type + "," + man + "," + getCurrentTime() + "," + logInfo + "\r\n";
   }
 
   public static void syslog(String type, String man, String logInfo) {
     logger.debug(getLoginfo(type, man, logInfo));
     writeLog(getFile("SYS"), getLoginfo(type, man, logInfo));
   }
 
   public static void applog(String type, String man, String logInfo) {
     logger.debug(getLoginfo(type, man, logInfo));
     writeLog(getFile("APP"), getLoginfo(type, man, logInfo));
   }
 
   public static void errlog(String type, String man, String logInfo) {
     logger.error(getLoginfo(type, man, logInfo));
     writeLog(getFile("ERR"), getLoginfo(type, man, logInfo));
   }
 
   public static void exception(Exception e) {
     exception("系统异常", "SYSTEM", "", e); }
 
   public static void exception(Exception e, String infoInfo) {
     exception("系统异常", "SYSTEM", infoInfo, e); }
 
   public static void exception(String type, String man, String logInfo, Exception e) {
     logger.error(getLoginfo(type, man, logInfo));
     writeLog(getFile("ERR"), getLoginfo(type, man, logInfo + " " + e.getMessage()));
     e.printStackTrace();
   }
 
   public static File[] getLogFileList(String type) {
     File[] fileList = new File[1];
     try {
       fileList = FileUtils.listFileByRegex(SystemProperties.getLogDir(), type.toUpperCase() + "-[\\d]{8}.log");
     } catch (Exception e) {
       exception(e);
       e.printStackTrace();
     }
 
     return FileUtils.sortByLastModified(fileList);
   }
 
   public static boolean deleteLogFile(String fileName) {
     boolean result = false;
     String filePath = SystemProperties.getLogDir() + "/" + fileName;
 
     if (!(fileName.equals(""))) {
       if (FileUtils.exists(filePath))
         result = FileUtils.delFile(filePath);
       else
         errlog("删除日志", "SYSTEM", "删除日志文件" + fileName + "不存在。");
     }
     else {
       errlog("删除日志", "SYSTEM", "没有指定删除日志文件。");
     }
 
     return result;
   }
 
   public static List<String> readLogFile(String fileName) throws IOException {
     String filePath = SystemProperties.getLogDir() + "/" + fileName;
 
     if (!(fileName.equals("")))
       if (!(FileUtils.exists(filePath)))
       {
         errlog("查看日志", "SYSTEM", "查看日志文件" + fileName + "不存在。");
       }
     else {
       errlog("查看日志", "SYSTEM", "没有指定查看日志文件名。");
     }
 
     File f = new File(filePath);
     FileReader read = new FileReader(f);
     BufferedReader br = new BufferedReader(read);
     List list = new ArrayList();
     while (true) {
       String line = br.readLine();
       if (line == null)
         break;
       if (!(line.trim().equals("")));
       list.add(line);
     }
 
     br.close();
     read.close();
 
     return list;
   }
 }




