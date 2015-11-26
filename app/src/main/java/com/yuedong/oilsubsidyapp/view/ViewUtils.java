package com.yuedong.oilsubsidyapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuedong.oilsubsidyapp.app.App;

/**
 * @author 俊鹏
 */
public class ViewUtils {
    private static LayoutInflater inflater = LayoutInflater.from(App.getInstance().getAppContext());

    public static <T extends View> T fvById(int id, View view) {
        return (T) view.findViewById(id);
    }

    public static View inflaterView(int id) {
        return inflater.inflate(id, null);
    }

    public static View inflaterView(int id, ViewGroup parentView) {
        return inflater.inflate(id, parentView, false);
    }


}
