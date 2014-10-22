package com.hbgz.pub.util;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
	/** 
	  * 字符串集合转换字符串(逗号分隔) 
	  * @author wyh 
	  * @param stringSet 
	  * @return 
	  */  
	 public static String makeStringByStringSet(Set<String> stringSet){  
	  StringBuilder str = new StringBuilder();  
	  int i=0;  
	  for(String s : stringSet){  
	   if(i == stringSet.size() - 1){  
	    str.append(s);  
	   }else{  
	    str.append(s + ",");  
	   }  
	   i++;  
	  }  
	  return str.toString().toLowerCase();  
	 }  
	   
	 /** 
	  * 获取拼音集合 
	  * @author wyh 
	  * @param src 
	  * @return Set<String> 
	  */  
	 public static Set<String> getPinyin(String src){  
	  if(src!=null && !src.trim().equalsIgnoreCase("")){  
	   char[] srcChar ;  
	   srcChar=src.toCharArray();  
	   //汉语拼音格式输出类  
	   HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();  
	  
	//输出设置，大小写，音标方式等  
	   hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
	   hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	   hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);  
	     
	   String[][] temp = new String[src.length()][];  
	   for(int i=0;i<srcChar.length;i++){  
	    char c = srcChar[i];  
	    //是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)  
	    if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")){  
	     try{  
	      temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);  
	     }catch(BadHanyuPinyinOutputFormatCombination e) {  
	      e.printStackTrace();  
	     }  
	    }else if(((int)c>=65 && (int)c<=90) || ((int)c>=97 && (int)c<=122)){  
	     temp[i] = new String[]{String.valueOf(srcChar[i])};  
	    }else{  
	     temp[i] = new String[]{""};  
	    }  
	   }  
	   String[] pingyinArray = Exchange(temp);  
	   Set<String> pinyinSet = new HashSet<String>();  
	   for(int i=0;i<pingyinArray.length;i++){  
	    pinyinSet.add(pingyinArray[i]);  
	   }  
	   return pinyinSet;  
	  }  
	  return null;  
	 }  
	   
	 /** 
	  * 递归 
	  * @author wyh 
	  * @param strJaggedArray 
	  * @return 
	  */  
	    public static String[] Exchange(String[][] strJaggedArray){  
	        String[][] temp = DoExchange(strJaggedArray);  
	        return temp[0];         
	    }  
	     
	    /** 
	     * 递归 
	     * @author wyh 
	     * @param strJaggedArray 
	     * @return 
	     */  
	    private static String[][] DoExchange(String[][] strJaggedArray){  
	        int len = strJaggedArray.length;  
	        if(len >= 2){             
	            int len1 = strJaggedArray[0].length;  
	            int len2 = strJaggedArray[1].length;  
	            int newlen = len1*len2;  
	            String[] temp = new String[newlen];  
	            int Index = 0;  
	            for(int i=0;i<len1;i++){  
	                for(int j=0;j<len2;j++){  
	                    temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];  
	                    Index ++;  
	                }  
	            }  
	            String[][] newArray = new String[len-1][];  
	            for(int i=2;i<len;i++){  
	                newArray[i-1] = strJaggedArray[i];                             
	            }  
	            newArray[0] = temp;  
	            return DoExchange(newArray);  
	        }else{  
	         return strJaggedArray;     
	        }  
	    }  
	     
	    /** 
	     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母 
	     *  
	     * @param chinese 
	     * @return 
	     */  
	    public static String spell(String chinese) {  
	        if (chinese == null) {  
	            return null;  
	        }  
	        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
	        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写  
	        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调  
	        format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v  
	        try {  
	            StringBuilder sb = new StringBuilder();  
	            for (int i = 0; i < chinese.length(); i++) {  
	                String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese  
	                        .charAt(i), format);  
	                if (array == null || array.length == 0) {  
	                    continue;  
	                }  
	                String s = array[0];// 不管多音字,只取第一个  
	                char c = s.charAt(0);// 大写第一个字母  
	                String pinyin = String.valueOf(c).toUpperCase().concat(s  
	                        .substring(1));  
	                sb.append(c);  
	            }  
	            return sb.toString();  
	        } catch (BadHanyuPinyinOutputFormatCombination e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	 /** 
	  * @param args 
	  */  
	 public static void main(String[] args) {  
	  String str = "单田芳";  
	  System.out.println(spell(str));  
	  
	}  
}
