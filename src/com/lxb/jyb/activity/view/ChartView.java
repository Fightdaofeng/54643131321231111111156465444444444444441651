package com.lxb.jyb.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.view.View;

public class ChartView extends View {
	private static final int Margin = 0;
	public int XPoint = 40; // 原点的X坐标
	public int YPoint = 260; // 原点的Y坐标
	public int XScale = 55; // X的刻度长度
	public int YScale = 40; // Y的刻度长度
	public int XLength = 380; // X轴的长度
	public int YLength = 240; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public String[] Data; // 数据
	public String Title; // 显示的标题

	public ChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawTable(canvas);
	}

	/**
	 * 绘制表格
	 * 
	 * @param canvas
	 */
	private void drawTable(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GRAY);
		Path path = new Path();
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setPathEffect(effects);
		// 纵向线
		for (int i = 1; i < 7; i++) {
			int startX = XPoint + i * XScale;
			int startY = YPoint;
			int stopY = YPoint - (this.YLabel.length - 1) * YScale;
			path.moveTo(startX, startY);
			path.lineTo(startX, stopY);
			canvas.drawPath(path, paint);
		}
		// 横向线
		for (int i = 1; i < 8; i++) {
			int startX = YPoint;
			int startY = YPoint - i * XScale;
			int stopX = YPoint + (this.YLabel.length - 1) * XScale;
			path.moveTo(startX, startY);
			path.lineTo(stopX, startY);
			paint.setColor(Color.DKGRAY);
			canvas.drawPath(path, paint);
			paint.setColor(Color.WHITE);
//			paint.setTextSize(ChartView.Margin / 2);
//			canvas.drawText(this.YLabel[i], ChartView.Margin / 4, startY
//					+ ChartView.Margin / 4, paint);
		}
	}

	// 画横纵轴
	private void drawXLine(Canvas canvas, Paint p) {
		canvas.drawLine(XPoint, YPoint, ChartView.Margin, ChartView.Margin, p);
		canvas.drawLine(XPoint, ChartView.Margin, XPoint - XPoint / 3, ChartView.Margin
				+ ChartView.Margin / 3, p);
		canvas.drawLine(XPoint, ChartView.Margin, XPoint + XPoint / 3, ChartView.Margin
				+ ChartView.Margin / 3, p);
	}

	private void drawYLine(Canvas canvas, Paint p) {
		canvas.drawLine(XPoint, YPoint, this.getWidth() - ChartView.Margin, YPoint,
				p);
		canvas.drawLine(this.getWidth() - ChartView.Margin, YPoint, this.getWidth()
				- ChartView.Margin - ChartView.Margin / 3, YPoint - ChartView.Margin / 3, p);
		canvas.drawLine(this.getWidth() - ChartView.Margin, YPoint, this.getWidth()
				- ChartView.Margin - ChartView.Margin / 3, YPoint + ChartView.Margin / 3, p);
	}

	// 画数据
	private void drawData(Canvas canvas) {
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.WHITE);
		p.setTextSize(ChartView.Margin / 2);
		// 纵向线
		for (int i = 1; i * XScale <= (this.getWidth() - ChartView.Margin); i++) {
			int startX = XPoint + i * XScale;
			canvas.drawText(this.XLabel[i], startX - ChartView.Margin / 4,
					this.getHeight() - ChartView.Margin / 4, p);
			canvas.drawCircle(startX, calY(Data[i]), 4, p);
			canvas.drawLine(XPoint + (i - 1) * XScale, calY(Data[i - 1]),
					startX, calY(Data[i]), p);
		}
	}

	/**
	 * 
	 * @param data2
	 * @return
	 */
	private float calY(String data2) {
		int y0 = 0;
		int y1 = 0;
		int parseInt = Integer.parseInt(data2);
		try {
			y0 = Integer.parseInt(YLabel[0]);
			y1 = Integer.parseInt(YLabel[1]);
		} catch (Exception e) {
			return 0;
		}
		try {
			return YPoint - ((parseInt - y0) * YScale / (y1 - y0));
		} catch (Exception e) {
			return 0;
		}
	}
}
