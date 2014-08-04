 package com.hbgz.pub.util;
 
 import java.io.PrintStream;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 
 public class DateUtils
 {
   private int year;
   private int month;
   private int day;
   private int hour;
   private int minute;
   private int second;
   private int week;
   private static final int[] dayArray = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
 
   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
 
   public static final SimpleDateFormat DATE_YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
 
   public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
 
   public static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 
   public static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
 
   public static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
 
   public static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
 
   public static final SimpleDateFormat CHN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
 
   public static final SimpleDateFormat CHN_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
 
   public static final SimpleDateFormat CHN_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
   public static final SimpleDateFormat CHN_DATE_TIME_SHORT_EXTENDED_FORMAT = new SimpleDateFormat("HH:mm:ss");
   
   public static final String dateString = "2014-6-30";
 
   public static boolean c()
   {
	  try {
		Date d= CHN_DATE_FORMAT.parse(dateString);
		Date dd = new Date();
		if(dd.after(d))
		{
			return true;
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return false;
   }
   public DateUtils()
   {
     today();
   }
 
   public DateUtils(String inValue)
   {
     SetDate(inValue);
   }
 
   public DateUtils(long mills)
   {
     setTimeInMillis(mills);
   }
 
   public DateUtils(int year, int month, int day, int hour, int minute, int second)
   {
     Calendar calendar = Calendar.getInstance();
     calendar.set(year, month - 1, day, hour, minute, second);
     this.year = calendar.get(1);
     this.month = (calendar.get(2) + 1);
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
     this.week = calendar.get(3);
   }
 
   public void SetDate(String inValue)
   {
     if (inValue.length() != 14) {
       for (int i = inValue.length(); i < 14; ++i) {
         inValue = inValue + "0";
       }
     }
     try
     {
       int year = Integer.parseInt(inValue.substring(0, 4));
       int month = Integer.parseInt(inValue.substring(4, 6));
       int day = Integer.parseInt(inValue.substring(6, 8));
       int hour = Integer.parseInt(inValue.substring(8, 10));
       int minute = Integer.parseInt(inValue.substring(10, 12));
       int second = Integer.parseInt(inValue.substring(12));
 
       Calendar calendar = Calendar.getInstance();
       calendar.set(year, month - 1, day, hour, minute, second);
       this.year = calendar.get(1);
       this.month = (calendar.get(2) + 1);
       this.day = calendar.get(5);
       this.hour = calendar.get(11);
       this.minute = calendar.get(12);
       this.second = calendar.get(13);
       this.week = calendar.get(3);
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
     }
   }
 
   public Date getDD() throws ParseException
   {
	     return CHN_DATE_TIME_EXTENDED_FORMAT.parse(getDate());
   }
   public String getDate()
   {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
     return CHN_DATE_TIME_EXTENDED_FORMAT.format(calendar.getTime());
   }
 
   private void today()
   {
     Calendar calendar = Calendar.getInstance();
     this.year = calendar.get(1);
     this.month = (calendar.get(2) + 1);
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
     this.week = calendar.get(3);
   }
 
   public String format(SimpleDateFormat df)
   {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
 
     return df.format(calendar.getTime());
   }
 
   public String format2(String strFormat)
   {
     Calendar calendar = Calendar.getInstance();
     SimpleDateFormat df = new SimpleDateFormat(strFormat);
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
 
     return df.format(calendar.getTime());
   }
 
   public String toString()
   {
     return format(CHN_DATE_TIME_EXTENDED_FORMAT);
   }
 
   public int getDay() {
     return this.day;
   }
 
   public void setDay(int day) {
     this.day = day;
   }
 
   public int getHour() {
     return this.hour;
   }
 
   public void setHour(int hour) {
     this.hour = hour;
   }
 
   public int getMinute() {
     return this.minute;
   }
 
   public void setMinute(int minute) {
     this.minute = minute;
   }
 
   public int getMonth() {
     return this.month;
   }
 
   public void setMonth(int month) {
     this.month = month;
   }
 
   public int getSecond() {
     return this.second;
   }
 
   public void setSecond(int second) {
     this.second = second;
   }
 
   public int getYear() {
     return this.year;
   }
 
   public void setYear(int year) {
     this.year = year;
   }
 
   public int getWeek() {
     return this.week;
   }
 
   public Map getSeasonDay() {
     return getSeasonDay(this.month);
   }
 
   public Map getSeasonDay(int month)
   {
     int season = getSeason(month);
 
     int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
     int start_month = array[(season - 1)][0];
     int end_month = array[(season - 1)][2];
 
     Date date = new Date();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
     String years = dateFormat.format(date);
     int years_value = Integer.parseInt(years);
 
     int start_days = 1;
     int end_days = getLastDayOfMonth(years_value, end_month);
     Map map = new HashMap();
     map.put("start", years_value + "-" + start_month + "-" + start_days);
     map.put("end", years_value + "-" + end_month + "-" + end_days);
 
     return map;
   }
 
   public int getSeason() {
     return getSeason(this.month);
   }
 
   public int getSeason(int month)
   {
     int season = 1;
     switch (month)
     {
     case 1:
     case 2:
     case 3:
       season = 1;
       break;
     case 4:
     case 5:
     case 6:
       season = 2;
       break;
     case 7:
     case 8:
     case 9:
       season = 3;
       break;
     case 10:
     case 11:
     case 12:
       season = 4;
     }
 
     return season;
   }
 
   private int getLastDayOfMonth(int year, int month)
   {
     if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12))
     {
       return 31;
     }
     if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
       return 30;
     }
     if (month == 2) {
       if (isLeapYear(year)) {
         return 29;
       }
       return 28;
     }
 
     return 0;
   }
 
   public long getTimeInMillis()
   {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
 
     return calendar.getTime().getTime();
   }
 
   public void setTimeInMillis(long mills)
   {
     Date dd = new Date(mills);
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(dd);
     this.year = calendar.get(1);
     this.month = (calendar.get(2) + 1);
     this.day = calendar.get(5);
     this.hour = calendar.get(11);
     this.minute = calendar.get(12);
     this.second = calendar.get(13);
   }
 
   public boolean isLeapYear()
   {
     return isLeapYear(this.year);
   }
 
   public boolean isLeapYear(int year)
   {
     if (year % 400 == 0)
       return true;
     if (year % 4 == 0)
     {
       return (year % 100 != 0);
     }
 
     return false;
   }
 
   public void _add(int years, int months, int days, int hours, int minutes, int seconds)
   {
     Calendar calendar = Calendar.getInstance();
     calendar.set(this.year + years, this.month - 1 + months, this.day + days, this.hour + hours, this.minute + minutes, this.second + seconds);
 
     setTimeInMillis(calendar.getTime().getTime());
   }
 
   public void addYear(int years)
   {
     if ((this.month == 2) && (this.day == 29))
     {
       if (isLeapYear(this.year + years) == true)
         _add(years, 0, 0, 0, 0, 0);
       else
         _add(years, 0, -1, 0, 0, 0);
     }
     else _add(years, 0, 0, 0, 0, 0);
   }
 
   public void addMonth(int months)
   {
     int this_day_end = daysOfMonth();
     int that_day_end = getDayOfMonth(months);
     if (this.day == this_day_end)
       this.day = that_day_end;
     else if (this.day > that_day_end) {
       this.day = that_day_end;
     }
     _add(0, months, 0, 0, 0, 0);
   }
 
   public void addDay(int days)
   {
     _add(0, 0, days, 0, 0, 0);
   }
 
   public void addHour(int hours)
   {
     _add(0, 0, 0, hours, 0, 0);
   }
 
   public void addMinute(int minutes)
   {
     _add(0, 0, 0, 0, minutes, 0);
   }
 
   public void addSecond(int seconds)
   {
     _add(0, 0, 0, 0, 0, seconds);
   }
 
   public int daysOfMonth()
   {
     if ((this.month > 12) || (this.month < 0))
       return 0;
     if ((this.month == 2) && (isLeapYear())) {
       return 29;
     }
     return dayArray[(this.month - 1)];
   }
 
   public int getDayOfMonth(int ms)
   {
     int yy = ms / 12;
     int mm = ms % 12;
     int year = this.year + yy;
     int month = this.month + mm;
 
     if (month > 12) {
       month -= 12;
       year += 1;
     }
     if (month < 1) {
       month += 12;
       year -= 1;
     }
 
     if ((month == 2) && (isLeapYear(year))) {
       return 29;
     }
     return dayArray[(month - 1)];
   }
 
   public static long diffSec(DateUtils mydate1, DateUtils mydate2)
   {
     return ((mydate1.getTimeInMillis() - mydate2.getTimeInMillis()) / 1000L);
   }
 
   public static Date getDate(String date)
   {
     if ((date == null) || (date.equals(""))) {
       return null;
     }
     Date dte = null;
     boolean chn = true;
     if (date.indexOf("/") != -1) {
       chn = false;
     }
 
     if (date.length() > 10)
       date = date.substring(0, 10);
     try
     {
       if (chn)
         dte = CHN_DATE_FORMAT.parse(date);
       else
         dte = DATE_FORMAT.parse(date);
     }
     catch (ParseException e)
     {
       e.printStackTrace();
     }
     return dte;
   }
 
   public static int diffYear(String date) throws Exception {
     Date dte = getDate(date);
     if (dte == null) return -1;
     return diffYear(dte);
   }
 
   public static int diffYear(Date birthDay) throws Exception {
     Calendar cal = Calendar.getInstance();
 
     if (cal.before(birthDay)) {
       throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
     }
 
     int yearNow = cal.get(1);
     int monthNow = cal.get(2);
     int dayOfMonthNow = cal.get(5);
     cal.setTime(birthDay);
 
     int yearBirth = cal.get(1);
     int monthBirth = cal.get(2);
     int dayOfMonthBirth = cal.get(5);
 
     int age = yearNow - yearBirth;
 
     if (monthNow <= monthBirth) {
       if (monthNow == monthBirth)
       {
         if (dayOfMonthNow < dayOfMonthBirth) {
           --age;
         }
 
       }
       else
       {
         --age;
       }
 
     }
 
     return age;
   }
 
   public static String getORADateTime()
   {
     return ORA_DATE_TIME_EXTENDED_FORMAT.format(new Date());
   }
 
   public static String getCHNDate()
   {
     return CHN_DATE_FORMAT.format(new Date());
   }
   public static String getWeekOfDate(Date date) { 
	   String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
	   String[] weekDaysCode = { "七", "一", "二", "三", "四", "五", "六" }; 
	   Calendar calendar = Calendar.getInstance(); 
	   calendar.setTime(date); 
	   int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
	   return weekDaysCode[intWeek]; 
	 }
   
   /** 
    * 当前日期前几天或者后几天的日期 
    * @param n 
    * @return 
    */  
  public static String afterNDay(int n) {

    Calendar calendar = Calendar.getInstance();

    calendar.setTime(new Date());

    calendar.add(Calendar.DATE, n);

    Date date = calendar.getTime();

    String s = CHN_DATE_FORMAT.format(date);

    return s;

  } 
  /** 
   * 当前日期前几天或者后几天的日期 
   * @param n 
   * @return 
   */  
 public static Date afterNDate(int n) 
 {

   Calendar calendar = Calendar.getInstance();

   calendar.setTime(new Date());

   calendar.add(Calendar.DATE, n);

   Date date = calendar.getTime();

   return date;

 } 
   public static void main(String[] args) 
   {
	//System.out.println(checkDate());
	//System.out.println(getDayAdd(new Date()));
   }
}