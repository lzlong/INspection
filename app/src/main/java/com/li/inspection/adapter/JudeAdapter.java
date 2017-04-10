package com.li.inspection.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;
import com.li.inspection.entity.Parameter;
import com.li.inspection.util.Utils;
import com.li.inspection.util.WPopupWindow;
import com.li.inspection.util.WheelView;

import java.util.List;

/**
 * Created by long on 17-1-13.
 */

public class JudeAdapter extends BaseArrayListAdapter {

    private Context mContext;

    public JudeAdapter(Context mContext, List<Parameter> list) {
        this.mContext = mContext;
        this.data = list;
    }

    @Override
    public ViewHolder getViewHolder(View convertView, ViewGroup parent, final int position) {
        ViewHolder mViewHolder = null;
        final Parameter parameter = (Parameter) data.get(position);
        mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.judge_item);
        TextView jude_item_t = mViewHolder.findViewById(R.id.jude_item_t);
        final TextView jude_item_j = mViewHolder.findViewById(R.id.jude_item_j);
        if (parameter.getIdqualified() == -1) {
            jude_item_j.setText("请判定");
            jude_item_j.setTextColor(mContext.getResources().getColor(R.color.jude_t));
            jude_item_j.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.c_corners_bg));
        } else if (parameter.getIdqualified() == 0){
            jude_item_j.setText("合　格");
            jude_item_j.setTextColor(mContext.getResources().getColor(R.color.white));
            jude_item_j.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.b_corners_bg));
        } else if (parameter.getIdqualified() == 1){
            jude_item_j.setText("不合格");
            jude_item_j.setTextColor(mContext.getResources().getColor(R.color.nok));
            jude_item_j.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.c_corners_bg));
        }
        final TextView jude_item_e = mViewHolder.findViewById(R.id.jude_item_e);
        jude_item_e.setVisibility(View.GONE);
        final LinearLayout jude_item_l = mViewHolder.findViewById(R.id.jude_item_l);
        LinearLayout jude_item_ok = mViewHolder.findViewById(R.id.jude_item_ok);
        LinearLayout jude_item_nok = mViewHolder.findViewById(R.id.jude_item_nok);
        jude_item_t.setText(parameter.getParameter());
        jude_item_l.setVisibility(View.GONE);
        jude_item_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jude_item_l.isShown()){
                    jude_item_l.setVisibility(View.GONE);
                } else {
                    jude_item_l.setVisibility(View.VISIBLE);
                }
            }
        });
        jude_item_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jude_item_j.setText("合　格");
                jude_item_j.setTextColor(mContext.getResources().getColor(R.color.white));
                jude_item_j.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.b_corners_bg));
                jude_item_l.setVisibility(View.GONE);
                parameter.setIdqualified(0);
            }
        });
        jude_item_nok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jude_item_j.setText("不合格");
                jude_item_j.setTextColor(mContext.getResources().getColor(R.color.white));
                jude_item_j.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.n_corners_bg));
                jude_item_l.setVisibility(View.GONE);
                parameter.setIdqualified(1);
            }
        });
        jude_item_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 3){
                    num = 0;
                    sum = 0;
                    color = "";
                    showColorPop(jude_item_e, Constants.VEHICLE_COLOR, parameter);
                } else if (position == 4){
                    showPop(jude_item_e, Constants.VEHICLE_PN, parameter);
                }
            }
        });
        if (position == 3){
            jude_item_e.setVisibility(View.VISIBLE);
            jude_item_e.setHint("请选择车辆颜色");
        } else if (position == 4){
            jude_item_e.setVisibility(View.VISIBLE);
            jude_item_e.setHint("请选择车辆和载人数");
        }else {
            jude_item_e.setVisibility(View.GONE);
        }
        return mViewHolder;
    }
    int num = 0;
    int sum = 0;
    String color = "";
    public void showColorPop(final TextView textView, final String[] data, final Parameter parameter){
        final View wh= LayoutInflater.from(mContext).inflate(R.layout.common_window_wheel,null);
        final WheelView picker= (WheelView) wh.findViewById(R.id.wheel);
        final TextView title = (TextView) wh.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("请选择车辆颜色种类的数量");
        picker.addData("1");
        picker.addData("2");
        picker.addData("3");
        picker.setCenterItem(data.length/3);
        final WPopupWindow popupWindow=new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView((Activity) mContext), Gravity.BOTTOM, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textView.setText((String)picker.getCenterItem());
//                parameter.setData((String)picker.getCenterItem());
                if (sum == 0){
                    sum = Integer.parseInt((String)picker.getCenterItem());
                    picker.clearData();
                    for (String name : data){
                        picker.addData(name);
                    }
                    picker.setCenterItem(data.length/3);
                    picker.notifyDataSetChanged();
                    title.setText("请选择第一种车辆颜色");
                } else if (sum != num){
                    color += picker.getCenterItem()+"/";
                    num ++;
                    picker.clearData();
                    for (String name : data){
                        picker.addData(name);
                    }
                    picker.setCenterItem(data.length/3);
                    picker.notifyDataSetChanged();
                    title.setText("已选择车辆颜色: "+color.substring(0,color.length()-1));
                }
                if (sum == num){
                    color = color.substring(0, color.length()-1);
                    textView.setText(color);
                    parameter.setData(color);
                    popupWindow.dismiss();
                }
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    public void showPop(final TextView textView, String[] data, final Parameter parameter){
        View wh= LayoutInflater.from(mContext).inflate(R.layout.common_window_wheel,null);
        final WheelView picker= (WheelView) wh.findViewById(R.id.wheel);
        for (String name : data){
            picker.addData(name);
        }
        picker.setCenterItem(data.length/3+1);
        final WPopupWindow popupWindow=new WPopupWindow(wh);
        popupWindow.showAtLocation(Utils.getContentView((Activity) mContext), Gravity.BOTTOM, 0, 0);
        wh.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText((String)picker.getCenterItem());
                parameter.setData((String)picker.getCenterItem());
                popupWindow.dismiss();
            }
        });
        wh.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
