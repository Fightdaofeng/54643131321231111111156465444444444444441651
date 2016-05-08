package com.lxb.jyb.tool;

import android.annotation.SuppressLint;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GetDate {
	public static String[] weeks;
	public static int year;
	public static int moth;
	public static int[] day;
	public static int today;

	public static int[] initDay(String date) {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date curDate = new Date(System.currentTimeMillis());// 当前时间
		//
		// date = formatter.format(curDate);

		String[] sql = date.split("-");
		year = Integer.parseInt(sql[0]);
		moth = Integer.parseInt(sql[1]);
		today = Integer.parseInt(sql[2]);
		int t = 0;

		switch (moth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			t = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			t = 30;
			break;
		case 2:
			if ((year % 4 == 0 && (year % 100) != 0) || year % 400 == 0)
				t = 29;
			else
				t = 28;
			break;
		}
		day = new int[t];
		for (int i = 1; i <= t; i++) {
			day[i - 1] = i;
		}
		System.out.println(moth + "月");
		System.out.println(today + "号");

		return day;
	}

	@SuppressLint("NewApi")
	public static String getWeekDay(int year, int moth, int day) {
		String[] weekDay = DateFormatSymbols.getInstance(Locale.CHINA)
				.getWeekdays();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, moth - 1);
		cal.set(Calendar.DATE, day);
		// 1-7对应的是星期日，星期一。。。。。星期六
		int week = cal.get(Calendar.DAY_OF_WEEK);

		String week1 = GetDate.weekDay(week - 1);
		return week1;
	}

	/**
	 * 转换星期格式
	 * 
	 * @param w
	 * @return
	 */
	public static String weekDay(int w) {
		// int t = (day + 2 * moth + 3 * (moth + 1) / 5 + year + year / 4 - year
		// / 100 + year / 400) % 7;
		// int countD = 0;
		// for (int i = 1; i < moth; i++) {
		// countD += getCount(year, moth);
		// }
		// countD += day;
		// int w = (year - 1) + ((year - 1) / 4) - ((year - 1) / 100) + (year -
		// 1)
		// / 400 + countD;
		String wk = "";
		switch (w % 7) {
		case 0:
			wk = "周日";
			break;

		case 1:
			wk = "周一";
			break;
		case 2:
			wk = "周二";
			break;
		case 3:
			wk = "周三";
			break;
		case 4:
			wk = "周四";
			break;
		case 5:
			wk = "周五";
			break;
		case 6:
			wk = "周六";
			break;
		}
		return wk;
	}

	public static String getMothString(String moth) {
		String m = null;
		switch (moth) {
		case "1":
			m = "    一月";
			break;

		case "2":
			m = "    二月";
			break;
		case "3":
			m = "    三月";
			break;
		case "4":
			m = "    四月";
			break;
		case "5":
			m = "    五月";
			break;
		case "6":
			m = "    六月";
			break;
		case "7":
			m = "    七月";
			break;
		case "8":
			m = "    八月";
			break;
		case "9":
			m = "    九月";
			break;
		case "10":
			m = "    十月";
			break;
		case "11":
			m = "  十一月";
			break;
		case "12":
			m = "  十二月";
			break;
		}
		return m;
	}

	public static int getCount(int year, int moth) {
		int co = 0;
		switch (moth) {
		case 1:

		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			co = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			co = 30;
			break;
		case 2:
			if ((year % 4 == 0 && (year % 100) != 0) || year % 400 == 0)
				co = 29;
			else
				co = 28;
			break;
		}
		return co;
	}

	public static String conversionDate(Date date) {
		String dt;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		dt = formatter.format(date);

		return dt;
	}
}
