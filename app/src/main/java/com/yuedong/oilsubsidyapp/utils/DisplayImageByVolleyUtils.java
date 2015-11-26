package com.yuedong.oilsubsidyapp.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yuedong.oilsubsidyapp.app.App;

/** 图片下载工具类 */
public class DisplayImageByVolleyUtils {
	/** 图片缓存大小 */
	public static final int IMAGE_LOAD_SIZE = 5 * 1024 * 1024;

	private DisplayImageByVolleyUtils() {
	};

	public static final ImageLoader IMAGELOADER = new ImageLoader(Volley.newRequestQueue(App.getInstance().getAppContext()),
			new BitmapCache());

	public static void loadImage(NetworkImageView niv, String url) {
		niv.setErrorImageResId(-1);
		niv.setDefaultImageResId(-1);
		niv.setImageUrl(url, IMAGELOADER);
	}

	public static void loadImage(NetworkImageView niv, String url, int defaultImage) {
		niv.setDefaultImageResId(defaultImage);
		niv.setErrorImageResId(defaultImage);
		niv.setImageUrl(url, IMAGELOADER);
	}

	public static void loadImage(NetworkImageView niv, String url, int defaultImage, int errorImage) {
		niv.setDefaultImageResId(defaultImage);
		niv.setErrorImageResId(errorImage);
		niv.setImageUrl(url, IMAGELOADER);
	}

	static final class BitmapCache implements ImageCache {
		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			mCache = new LruCache<String, Bitmap>(IMAGE_LOAD_SIZE) {
				@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getByteCount();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			if (bitmap != null) {
				mCache.put(url, bitmap);
			}
		}
	}
}
