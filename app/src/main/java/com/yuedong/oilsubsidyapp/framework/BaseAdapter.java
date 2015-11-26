package com.yuedong.oilsubsidyapp.framework;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 俊鹏
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected/* 可以給子类使用 */ List<T> mDatas;
    protected Context mCon;
    protected static LayoutInflater mInflater;
    private int layoutId;

    public List<T> getCommonContainer() {
        return mDatas;
    }

    public void addToContainer(T t) {
        if (!mDatas.contains(t)) {
            mDatas.add(t);
        }
    }

    public void deleteFromContainer(T t) {
        if (mDatas.contains(t)) {
            mDatas.remove(t);
        }
    }

    public void updateListView() {
        notifyDataSetChanged();
    }

    public View inflaterView(int res) {
        return mInflater.inflate(res, null);
    }

    public BaseAdapter(Context con, List<T> data, int layouid) {
        this.mDatas = data;
        this.mCon = con;
        mInflater = LayoutInflater.from(con);
        this.layoutId = layouid;
    }

    public BaseAdapter(Context con, int layoutId) {
        this(con, null, layoutId);
    }

    public void setData(List<T> data) {
        if (data != null && !data.isEmpty()) {
            this.mDatas = data;
        }
    }

    public void setData(T[] ts) {
        if (ts != null && ts.length > 0) {
            mDatas = new ArrayList<T>();
            for (T t : ts) {
                this.mDatas.add(t);
            }
        }
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mCon, convertView, parent, layoutId, position);
        convert(viewHolder, getItem(position), position, convertView);
        return viewHolder.getConvertView();
    }

    abstract public void convert(ViewHolder viewHolder, T t, int position, View convertView);

}
