package com.yuedong.oilsubsidyapp.framework;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 俊鹏
 */
public abstract class AbstractPagerAdapter extends PagerAdapter {
	/** 是否无缝 */
	private boolean mSeamless = false;
	/** 一共页数 */
	private int mCounts = 0;

	public void setSeamless(boolean seamless) {
		this.mSeamless = seamless;
	}

	public AbstractPagerAdapter(int counts) {
		if (counts > 0)
			this.mCounts = counts;
	}

	@Override
	public int getCount() {
		return !mSeamless ? mCounts : Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView((View) getView(container, position));
		return getView(container, position);
	}

	public abstract Object getView(ViewGroup container, int position);

}
