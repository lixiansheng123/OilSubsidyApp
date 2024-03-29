package com.yuedong.oilsubsidyapp.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.yuedong.oilsubsidyapp.app.App;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 系统管理类
 */
@SuppressWarnings("deprecation")
public final class SystemUtil {
	private static final String TAG = "SystemManage";

	/** 拨打电话 */
	private static final String TEL_PRE = "tel:";

	/** 发发送短信 */
	private static final String SMS_PRE = "smsto";

	/** 短信消息 */
	private static final String EXTRA_SMS_BODY = "sms_body";

	private SystemUtil() {
	}

	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param phoneNo
	 *            [参数说明]
	 * @return void [返回类型说明]
	 */
	public static void call(Context context, String phoneNo) {
		Uri uri = Uri.parse(TEL_PRE + phoneNo.trim());
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		context.startActivity(intent);
	}

	/**
	 * 发�?短信
	 * 
	 * @param context
	 * @param phoneNo
	 * @param content
	 *            [参数说明]
	 */
	public static void sms(Context context, String phoneNo, String content) {
		Uri uri = Uri.fromParts(SMS_PRE, phoneNo.trim(), null);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra(EXTRA_SMS_BODY, content.trim());
		context.startActivity(intent);
	}

	/**
	 * 获取手机IMS> <br>
	 * 
	 * @return String [返回IMSI,有可能返回null]
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei != null) {
			if (imei.length() > 15) {
				imei = imei.substring(0, 15);
			}
		} else {
			// 有些平板没有IMEI码，没有就把IMEI设为15个0
			imei = "000000000000000";
		}

		return imei;
	}

	/**
	 * 获取手机号码 <br/>
	 * 
	 * @return String [返回手机号码]
	 */
	public static String getPhoneNum(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 获取手机MAC地址
	 * 
	 * @return [参数说明]
	 * @return String [返回MAC]
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();

		return info.getMacAddress();
	}

	/**
	 * 获取手机本地IP地址
	 * 
	 * @return 本机IP地址,如果获取不到网络信息则返回：27.0.0.1
	 */
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface info = en.nextElement();
				Enumeration<InetAddress> enAddr = info.getInetAddresses();
				while (enAddr.hasMoreElements()) {
					InetAddress addr = enAddr.nextElement();
					if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
						return addr.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
		}
		return "127.0.0.1";
	}

	/**
	 * 跳转到网络设置页面
	 * 
	 * @param context
	 *            [参数说明]
	 */
	public static void gotoNetworkSetting(Context context) {
		Intent i = new Intent();
		i.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	/**
	 * 获取客户端的版本代号
	 * 
	 * @param context
	 * @return int [VersionCode]
	 */
	public static int getVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		int versionCode;
		try {
			PackageInfo info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
			versionCode = info.versionCode;

		} catch (NameNotFoundException e) {
			versionCode = 0;
		}
		return versionCode;
	}

	/**
	 * 获取客户端的版本名称
	 * 
	 * @param context
	 * @return int [VersionCode]
	 */
	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		String versionName;
		try {
			PackageInfo info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
			versionName = info.versionName;

		} catch (NameNotFoundException e) {
			versionName = "--";
		}
		return versionName;
	}

	/**
	 * 判断是否有可用网用
	 * 
	 * @param context
	 * @return [参数说明]
	 * @return boolean [返回类型说明]
	 */
	public static boolean checkNetWorkStatue(Context context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isConnected();
		}
		return netSataus;
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return 如果SD卡可用返回true,否则返回false
	 */
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机内存可用空间
	 * 
	 * @return [参数说明]
	 * @return long [返回类型说明]
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blocksize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blocksize * availableBlocks;
	}

	/**
	 * 获取SD卡的可用空间
	 * 
	 * @return [参数说明]
	 * @return long [返回类型说明]
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(sdcardDir.getPath());
			long blockSize = statFs.getBlockSize();
			long availableBlocks = statFs.getAvailableBlocks();
			return blockSize * availableBlocks;
		} else {
			return -1;
		}
	}

	/**
	 * 判断定位服务是否可用
	 * 
	 * @param context
	 * @return 可用返回true,否则返回false
	 */
	public static boolean isLocationServiceAvailable(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean gpsEnabled = false;
		boolean netEnabled = false;
		try {
			gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			// ignore
		}
		try {
			netEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception e) {
			// ignore
		}
		return gpsEnabled || netEnabled;
	}

	/**
	 * 定位服务设置页面
	 * 
	 * @param context
	 */
	public static void gotoLocationSettings(Context context) {
		Intent intent = new Intent();
		intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 获取R资源的id
	 * 
	 * @param name
	 *            资源名称
	 * @param type
	 *            资源类型。详见R.class内：anim 、drawable...
	 * @param defaulRes
	 *            默认的返回资源id
	 */
	public static int getIdentifier(Context context, String name, String type, int defaulRes) {
		Resources res = context.getResources();
		int resid = res.getIdentifier(name, type, context.getPackageName());
		if (resid == 0) {
			resid = defaulRes;
		}

		return resid;
	}

	/**
	 * 获取drawable资源id,找不到返回defaulRes
	 * 
	 * @param name
	 *            资源名称
	 * @param defaulRes
	 *            默认返回资源
	 */
	public static int getIdentifierDrawable(String name, int defaulRes) {
		return getIdentifier(App.getInstance().getAppContext(), name, "drawable", defaulRes);
	}

	/**
	 * 获取drawable资源id,找不到返回0
	 * 
	 * @param name
	 *            资源名称
	 */
	public static int getIdentifierDrawable(String name) {
		return getIdentifierDrawable(name, 0);
	}

	/**
	 * 获取屏幕材质信息
	 */
	public static DisplayMetrics getDisplayMetrics() {
		return App.getInstance().getAppContext().getResources().getDisplayMetrics();
	}

	/**
	 * wifi控制 注册需要增加权限
	 * 
	 * @param isOpenWifi
	 *            是否开启wifi
	 */
	public static void wifiControl(boolean isOpenWifi, Context con) {
		WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null) {
			wifiManager.setWifiEnabled(isOpenWifi);
		}
	}

	/**
	 * 获取系统图片 只获取jpg，png的图片
	 */
	public static List<String> getSystemPic(Activity act) {
		List<String> filterPic = new ArrayList<String>();
		Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver contentResolver = act.getContentResolver();
		Cursor cursor = contentResolver.query(imageUri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
		while (cursor.moveToNext()) {
			String picPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			com.yuedong.oilsubsidyapp.utils.L.e("pictrue path :" + picPath);
			filterPic.add(picPath);
		}
		return filterPic;
	}

	/** 获取手机媒体音量大小 */
	public static int getMobileMediaVolume(Context con) {
		AudioManager manager = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
		return manager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/** 通知更新相册 */
	public static void notifyPicSetByFile(Context context, File file) {
		if (file != null && file.exists()) {
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			context.sendBroadcast(intent);
		}
	}

}
