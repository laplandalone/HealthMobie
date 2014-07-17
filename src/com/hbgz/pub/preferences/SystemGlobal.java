/*     */ package com.hbgz.pub.preferences;
/*     */ 
/*     */ import java.net.URL;
import java.util.Properties;

import com.hbgz.pub.util.FileUtils;
/*     */ 
/*     */ public class SystemGlobal
/*     */   implements VariableStore
/*     */ {
/*  16 */   private static SystemGlobal SYSTEMGLOBAL = new SystemGlobal();
/*  17 */   private static Properties CONFIGS_PROPERTIES = null;
/*     */   private VariableExpander EXPANDER;
/*     */ 
/*     */   public SystemGlobal()
/*     */   {
/*  18 */     this.EXPANDER = new VariableExpander(this, "${", "}");
/*     */   }
/*     */ 
/*     */   private void initKeys()
/*     */   {
/*  24 */     URL url = super.getClass().getResource("/global.properties");
/*     */ 	
/*  26 */     String path = url.getFile();
/*     */     try {
/*  28 */       CONFIGS_PROPERTIES = FileUtils.readProperties(path);
/*     */     } catch (Exception e) {
/*  30 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String get(String field)
/*     */   {
/*  42 */     if (CONFIGS_PROPERTIES == null) {
/*  43 */       SYSTEMGLOBAL.initKeys();
/*     */     }
System.out.println("SYSTEMGLOBAL:"+SYSTEMGLOBAL);
/*  45 */     return SYSTEMGLOBAL.getVariableValue(field);
/*     */   }
/*     */ 
/*     */   public static Integer getInteger(String field)
/*     */   {
/*  56 */     int value = -1;
/*  57 */     String str = get(field);
/*  58 */     if ((str != null) && (!("".equals(str))))
/*     */       try {
/*  60 */         value = Integer.valueOf(str).intValue();
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*  64 */     return Integer.valueOf(value);
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(String field)
/*     */   {
/*  75 */     boolean value = false;
/*  76 */     String str = get(field);
/*  77 */     if ((str != null) && (!("".equals(str))))
/*     */       try {
/*  79 */         value = Boolean.valueOf(str).booleanValue();
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*  83 */     return value;
/*     */   }
/*     */ 
/*     */   public String getVariableValue(String variableName)
/*     */   {
/*  93 */     String preExpansion = CONFIGS_PROPERTIES.getProperty(variableName);
/*     */     System.out.println("preExpansion:"+preExpansion);
			  System.out.println("CONFIGS_PROPERTIES:"+CONFIGS_PROPERTIES);
/*  95 */     if (preExpansion == null) {
/*  96 */       return null;
/*     */     }
/*     */ 
/*  99 */     return this.EXPANDER.expandVariables(preExpansion);
/*     */   }
/*     */ 
/*     */   public static void set(String key, String value)
/*     */   {
/* 113 */     CONFIGS_PROPERTIES.setProperty(key, value);
/*     */   }
/*     */ }

/* Location:           D:\CommitX9\CommitX9MY\WEB-INF\classes\
 * Qualified Name:     com.shcommit.tools.preferences.SystemGlobal
 * JD-Core Version:    0.5.3
 */