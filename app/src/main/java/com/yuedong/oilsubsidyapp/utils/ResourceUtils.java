package com.yuedong.oilsubsidyapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.yuedong.oilsubsidyapp.app.App;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ResourceUtils {
    private ResourceUtils() {
    }

    ;

    public static int getColor(int resId) {
        return App.getInstance().getAppContext().getResources().getColor(resId);
    }

    public static int getDimen(int resId) {
        return Math.round(App.getInstance().getAppContext().getResources().getDimension(resId));
    }

    public static String getString(int resId) {
        return App.getInstance().getAppContext().getResources().getString(resId);
    }

    public static Drawable getDrawable(int resId) {
        return App.getInstance().getAppContext().getResources().getDrawable(resId);
    }

    public static String[] getStringArray(int resId) {
        return App.getInstance().getAppContext().getResources().getStringArray(resId);
    }

    /**
     * 从raw文件获取字符
     *
     * @param resId
     * @return
     */
    public static String getFromRaw(int resId) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    App.getInstance().getAppContext().getResources().openRawResource(resId));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    /**
     * 从assets 文件夹生成字符
     *
     * @param fileName
     * @return
     */
    public static String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    App.getInstance().getAppContext().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    /**
     * 把dir下所有的文件路径返回
     *
     * @param dir
     * @param context
     * @return
     */
    public static String[] getFromAssets(String dir, Context context) {
        String[] str = null;
        try {
            str = context.getAssets().list(dir);
        } catch (Exception e) {
        }
        return str;

    }

}
