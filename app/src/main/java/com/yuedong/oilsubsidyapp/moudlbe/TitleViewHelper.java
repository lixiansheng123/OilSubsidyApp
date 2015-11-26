package com.yuedong.oilsubsidyapp.moudlbe;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuedong.oilsubsidyapp.R;
import com.yuedong.oilsubsidyapp.view.ViewUtils;

/**
 * @author 俊鹏
 */
public class TitleViewHelper {
    private static TitleViewHelper instance;

    private TitleViewHelper() {
    }

    public static TitleViewHelper getInstance() {
        if (instance == null) {
            synchronized (TitleViewHelper.class) {
                if (instance == null) {
                    instance = new TitleViewHelper();
                }
            }
        }
        return instance;
    }

    /* 返回按钮，标题，右边的图标和文字*/
    private View defaultView;
    private ImageView defaultBbackView;
    private TextView defaultTitle;
    private ImageView defaultRightMoreEvent;
    private TextView defaultRightJumpText;
    private LinearLayout defaultLeftClickLl;
    private LinearLayout defaultRightClickLl;

    /**
     * 比较标准的bar含有回退按钮标题和右边的小图标
     */
    public void defaultTitle(int backIcon, CharSequence title, int rightIcon, View.OnClickListener rightLner) {
        defaultTitle(backIcon, title, rightIcon, null, rightLner);
    }

    /**
     * 比较标准的bar含有回退按钮标题和右边的小图标
     */
    public void defaultTitle(int backIcon, CharSequence title, int rightIcon, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        initDefaultView();
        hideTotalView((ViewGroup) defaultView);
        defaultBbackView.setVisibility(View.VISIBLE);
        defaultTitle.setVisibility(View.VISIBLE);
        defaultRightMoreEvent.setVisibility(View.VISIBLE);
        defaultBbackView.setImageResource(backIcon);
        defaultTitle.setText(title);
        defaultRightMoreEvent.setImageResource(rightIcon);
        if (leftListener != null) {
            defaultBbackView.performClick();
            defaultLeftClickLl.setOnClickListener(leftListener);
        } else {
            // 默认回退
        }

        if (rightListener != null) {
            defaultRightMoreEvent.performClick();
            defaultRightClickLl.setOnClickListener(rightListener);
        }
    }

    /**
     * 标准2 含有回退按钮，标题
     */
    public void defaultTitle2(int backIcon, CharSequence title) {
        defaultTitle2(backIcon, title, null);
    }

    /**
     * 标准2 含有回退按钮，标题
     */
    public void defaultTitle2(int backIcon, CharSequence title, View.OnClickListener listener) {
        initDefaultView();
        hideTotalView((ViewGroup) defaultView);
        defaultBbackView.setVisibility(View.VISIBLE);
        defaultTitle.setVisibility(View.VISIBLE);
        defaultBbackView.setImageResource(backIcon);
        defaultTitle.setText(title);
        if (listener != null) {
            defaultLeftClickLl.setOnClickListener(listener);
        } else {
            // 默认回退上一页
        }
    }


    private void initDefaultView() {
        defaultView = ViewUtils.inflaterView(R.layout.layout_headview_normal);
        defaultBbackView = ViewUtils.fvById(R.id.id_likeBack_headView, defaultView);
        defaultTitle = ViewUtils.fvById(R.id.id_title_headView, defaultView);
        defaultRightJumpText = ViewUtils.fvById(R.id.tv_right_text, defaultView);
        defaultRightMoreEvent = ViewUtils.fvById(R.id.iv_more_headview, defaultView);
        defaultLeftClickLl = ViewUtils.fvById(R.id.ll_left_layout, defaultView);
        defaultRightClickLl = ViewUtils.fvById(R.id.ll_right_layout, defaultView);
    }

    /**
     * 隐藏所有的控件
     *
     * @param rootView
     */
    private void hideTotalView(ViewGroup rootView) {
        int childCount = rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = rootView.getChildAt(i);
            if (childView instanceof ViewGroup) {
                hideTotalView((ViewGroup) childView);
            } else {
                childView.setVisibility(View.GONE);
            }
        }
    }

}
