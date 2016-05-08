package com.lxb.jyb.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.lxb.jyb.bean.JYPZBean;

import android.content.Context;
import jxl.Sheet;
import jxl.Workbook;

public class SymbolUtil {
	private static ArrayList<String> list1;
	private static ArrayList<String> list2;

	public static ArrayList<JYPZBean> parseExcel(Context context,
			ArrayList<String> codelist) {
		ArrayList<JYPZBean> beanlist = new ArrayList<JYPZBean>();
		try {
			Workbook workbook = null;
			InputStream in = null;
			try {
				in = context.getAssets().open("jypz.xls");
				workbook = Workbook.getWorkbook(in);
				workbook.getNumberOfSheets();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("file not found!");
			}
			Sheet sheet = workbook.getSheet(0);
			int columnCount = sheet.getColumns();
			int rowCount = sheet.getRows();
			System.out.println("当前工作表的名字:" + sheet.getName());
			System.out.println("总行数:" + rowCount);
			System.out.println("总列数:" + columnCount);
			list1 = new ArrayList<String>();
			list2 = new ArrayList<String>();
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					// 注意，这里的两个参数，第一个是表示列的，第二才表示行
					String s = sheet.getCell(0, i).getContents();
					String t = sheet.getCell(1, i).getContents();
					list1.add(s);
					list2.add(t);
				}
			}
			workbook.close();
			for (int i = 0; i < codelist.size(); i++) {
				for (int k = 0; k < list1.size(); k++) {
					if (list1.get(k).equals(codelist.get(i))) {
						JYPZBean bean = new JYPZBean(codelist.get(i),
								list2.get(k), true);
						beanlist.add(bean);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanlist;
	}
}
