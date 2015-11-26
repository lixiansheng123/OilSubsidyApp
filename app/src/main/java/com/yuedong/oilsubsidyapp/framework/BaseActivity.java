package com.yuedong.oilsubsidyapp.framework;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuedong.oilsubsidyapp.utils.L;
import com.yuedong.oilsubsidyapp.utils.WindowUtils;
import com.yuedong.oilsubsidyapp.view.ViewUtils;

public class BaseActivity extends AppCompatActivity {
    protected Integer[] windowWh;
    protected RelativeLayout mMainLayout;
    protected LinearLayout mTitleLayout;
    protected LinearLayout mContentLayout;
    /**
     * 給内容区域填充一个遮罩 具体内容由子类设定
     */
    protected LinearLayout mContentMask;
    private static final int ID_TITLE = 0x001;
    private static final int ID_CONTENT = 0x002;
    private static final int ID_CONTENT_MASK = 0x003;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowWh = WindowUtils.getPhoneWH(this);
        L.i("手机屏幕宽高:" + windowWh[0] + " * " + windowWh[1]);
        initUi();
    }

    private void initUi() {
        mMainLayout = new RelativeLayout(this);
        mTitleLayout = new LinearLayout(this);
        mContentLayout = new LinearLayout(this);
        mContentMask = new LinearLayout(this);
        // 统一设一个id
        mTitleLayout.setId(ID_TITLE);
        mContentLayout.setId(ID_CONTENT);
        mContentMask.setId(ID_CONTENT_MASK);
        RelativeLayout.LayoutParams titleLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mMainLayout.addView(mTitleLayout, titleLp);
        RelativeLayout.LayoutParams contentLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        contentLp.addRule(RelativeLayout.BELOW, mTitleLayout.getId());
        mMainLayout.addView(mContentLayout, contentLp);
        setContentView(mMainLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 增加内容填充区域
     *
     * @param view
     */
    public void setContentMaskView(View view) {
        RelativeLayout.LayoutParams contentMaskLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        contentMaskLp.addRule(RelativeLayout.BELOW, mTitleLayout.getId());
        mMainLayout.addView(mContentMask, contentMaskLp);
        mContentMask.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置显示的内容
     *
     * @param resId
     */
    public void setContentView(int resId) {
        if (resId < 0)
            return;
        setContentView(ViewUtils.inflaterView(resId, mContentLayout));
    }

    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化title布局
     */
    protected void initTitleView(View titleView) {
        mTitleLayout.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (windowWh[1] / 8 + 0.5f)));
    }


}
