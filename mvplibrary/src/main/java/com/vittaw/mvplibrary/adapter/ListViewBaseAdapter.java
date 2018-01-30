package com.vittaw.mvplibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20 0020.
 * 不同的是具体的item_view,和具体的数据
 *          holder              Model
 */

public abstract class ListViewBaseAdapter<T> extends BaseAdapter {

    private List<T> data;

    private LayoutInflater inflater;

    private int[] layoutResId;//缺啥补啥,传值传进来

    public ListViewBaseAdapter(Context context, List<T> data, int ... layoutResId){
        inflater = LayoutInflater.from(context);
        if (data != null) {
            this.data = data;
        }else{
            this.data = new ArrayList<>();
        }
        this.layoutResId = layoutResId;
    }

    @Override
    public int getViewTypeCount() {
        return layoutResId.length;
    }


    /**
     * 要返回的是:
     *      当前item对象,所对应的item样式;
     *      这个样式放在当前item对象的type字段内(一般来说)
     *
     *
     *      用反射的方法,拿到这个字段的值,将type字段的值返回
     *
     *
     * type 必须从0开始, 0,1,2,3...
     *
     * 根据位置获取对应元素的View的类型
     * ①获取对应位置的对象
     * ②获取对象的type属性
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int type = 0;
        //获取对应位置的对象
        T t = getItem(position);
        //获取t对象的class
        Class<?> tClass = t.getClass();
        try {
            //获取指定对象中的type字段
            Field field = tClass.getDeclaredField("type");
            //添加访问权限
            field.setAccessible(true);
            //从指定对象中 获取指定字段的值
            type = field.getInt(t);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return type;
    }

    //我们只是对convertView做了一个简单的封装
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * 数据加载
         *      ①View
         *      ②数据
         */
        ViewHolder holder = null;
        int itemViewType = getItemViewType(position);//当前item的布局
        if (convertView == null){
            convertView = inflater.inflate(layoutResId[itemViewType], parent, false);
            holder = new ViewHolder(convertView);//convertView 填充的布局不同,holder里面的findView找到的item也不同,后续在bindView时做判断
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //数据加载
        bindData(holder,getItem(position),position);
        return convertView;
    }

    //protected 只能让子类去实现
    /**
     * 数据加载:view+item 数据+ position
     * @param holder
     * @param item
     * @param position
     */
    protected abstract void bindData(ViewHolder holder, T item, int position);

    /**
     * ViewHolder 持有我们的convertView即itemView---进而持有itemView的childView
     */
    public static class ViewHolder{

        public View itemView;

        //使用Map来对已经实例化过的View做一个缓存
        private Map<Integer,View> cacheViews;

        public ViewHolder(View itemView){
            this.itemView = itemView;
            cacheViews = new HashMap<>();
        }

        //根据id获取convertView中查找实例
        public View findView(int resId){
            //直接去缓存中找
            View v = null;
            if (cacheViews.containsKey(resId)) {//找到了
                v = cacheViews.get(resId);
            }else{//没找到
                v = itemView.findViewById(resId);
                cacheViews.put(resId,v);
            }
            return v;
        }

        public void setText(int resId,String text){
            TextView textView = (TextView) findView(resId);
            textView.setText(text);
        }

    }


    @Override
    public int getCount() {
        return data != null?data.size():0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateRes(List<T> data){
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addRes(List<T> data){
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

}
