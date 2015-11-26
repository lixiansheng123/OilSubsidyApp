package com.yuedong.oilsubsidyapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class WindowUtils {
	/**
	 * 获取手机的宽高
	 * 
	 * @param context
	 */

	public static Integer[] getPhoneWH(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		int width2 = dm.widthPixels;
		int height2 = dm.heightPixels;
		// PhoneWH wh = new PhoneWH(width2, height2);
		Integer[] it = new Integer[2];
		it[0] = width2;
		it[1] = height2;
		return it;
	};

	/**
	 * 判断是否为平板 好像有点问题最好别用咯
	 * 
	 * @return
	 */
	public static boolean isPad(Context con) {
		WindowManager wm = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		// 屏幕宽度
		float screenWidth = display.getWidth();
		// 屏幕高度
		float screenHeight = display.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		// 屏幕尺寸
		double screenInches = Math.sqrt(x + y);
		// 大于6尺寸则为Pad
		if (screenInches >= 6.0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是平板
	 */
	public static boolean isPad2(Context context) {
		return (context.getResources().getConfiguration().screenLayout & //
		Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
