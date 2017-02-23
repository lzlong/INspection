package com.li.inspection.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;
import com.li.inspection.util.ImageUtil;
import com.li.inspection.util.Utils;

/**
 * Created by long on 17-1-14.
 */

public class PhotoActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        initView();
    }

    private ImageView back_tv;
    private TextView name_tv, btn_tv;
    private ImageView photo;
    private void initView() {
        back_tv = (ImageView) findViewById(R.id.back_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        name_tv.setText("图片预览");
        btn_tv = (TextView) findViewById(R.id.btn_tv);
        btn_tv.setText("确定");
        photo = (ImageView) findViewById(R.id.photo);
        long time = System.currentTimeMillis();
        if (bitmap != null){
            bitTag = 0;
            if (tag != 2){
                Bitmap bit = Utils.decodeSampledBitmapFromResource(getResources(),
                        R.mipmap.timg, 100, 100);
                bitmap = ImageUtil.createWaterMaskLeftTop(PhotoActivity.this, bitmap, bit);
                bitmap = ImageUtil.drawTextToTopCenter(PhotoActivity.this, bitmap, VIN, 25);
                bitmap = ImageUtil.drawTextToBottomCenter(PhotoActivity.this, bitmap, "邯郸交警支队车管所", 25);
                bitmap = ImageUtil.drawTextToRightBottom(PhotoActivity.this, bitmap, Utils.getTime(), 15);
                bitmap = ImageUtil.drawGpsToRightBottom(PhotoActivity.this, bitmap, Constants.Gps, 15);
            }
            photo.setImageBitmap(bitmap);
        }
        back_tv.setOnClickListener(this);
        btn_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back_tv){
            if (tag == 2){
                Intent intent = new Intent(PhotoActivity.this, PhotoActivity_b.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(PhotoActivity.this, PhotoActivity_a.class);
                startActivity(intent);
            }
            finish();
        } else if (v == btn_tv){
            ImageUtil.save(bitmap, tag);
            finish();
        }
    }
}
