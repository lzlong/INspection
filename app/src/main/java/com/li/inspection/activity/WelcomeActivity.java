package com.li.inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.li.inspection.R;
import com.li.inspection.util.GifView;

/**
 * Created by long on 17-1-9.
 */

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
    }

    private GifView welcome_gif;
    private Button intent_btn;

    private void init() {
        welcome_gif = (GifView) findViewById(R.id.welcome_gif);
        welcome_gif.setMovieResource(R.mipmap.welcom_gif_pic);
        intent_btn = (Button) findViewById(R.id.intent_btn);
        intent_btn.setOnClickListener(this);
//        welcome_gif.setMovieTime(1280);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1280);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(1));
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                handler.sendMessage(handler.obtainMessage(0));
//            }
//        }).start();

    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                welcome_gif.setPaused(true);
                return;
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v == intent_btn){
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
