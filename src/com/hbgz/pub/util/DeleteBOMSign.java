 package com.hbgz.pub.util;
 
 import java.io.File;
 import java.io.RandomAccessFile;
 import java.nio.ByteBuffer;
 import java.nio.channels.FileChannel;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class DeleteBOMSign
 {
   private static final Log log = LogFactory.getLog(DeleteBOMSign.class);
 
   public static boolean deleteBOM(String filename) {
     return deleteBOM(new File(filename));
   }
 
   public static boolean deleteBOM(File file)
   {
     boolean result = false;
     if (file.exists())
       if (file.isFile()) {
         int size = (int)file.length();
         byte[] source = new byte[size];
         try
         {
           RandomAccessFile raf = new RandomAccessFile(file, "r");
           raf.read(source, 0, size);
           raf.close();
           if (isBomSign(source)) {
             log.debug("File have BOM sign,file name : [" + file.getPath() + "] .");
             FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
             fc.write(ByteBuffer.wrap(source, 3, size - 3));
             fc.truncate(size - 3);
             fc.close();
           }
           result = true;
         }
         catch (Exception e) {
           result = false;
         }
       } else {
         result = false;
       }
     else {
       result = false;
     }
 
     return result;
   }
 
   public static boolean isBomSign(byte[] source) {
     boolean isBom = false;
     if ((source[0] < 0) && (source[1] < 0) && (source[2] < 0)) {
       isBom = true;
     }
     return isBom;
   }
 
   static void getDir(String strPath) throws Exception {
     try {
       File f = new File(strPath);
       if (f.isDirectory()) {
         File[] fList = f.listFiles();
         for (int j = 0; j < fList.length; ++j) {
           if (fList[j].isDirectory()) {
             getDir(fList[j].getPath());
           }
         }
         for (int j = 0; j < fList.length; ++j)
         {
           if ((!(fList[j].isFile())) || 
             (!(fList[j].getName().matches(".*\\.java$")))) continue;
           deleteBOM(fList[j]);
         }
       }
 
     }
     catch (Exception e)
     {
       System.out.println("Error£º " + e);
     }
   }
 
   public static void main(String[] args)
   {
     String strPath = "D:\\workspace\\CommitX9\\src";
     log.debug("Scan BOM file path :" + strPath);
     try {
       log.debug("Starting to scan file...");
       getDir(strPath);
       log.debug("Finished to scan file.");
     }
     catch (Exception e)
     {
     }
   }
 }
