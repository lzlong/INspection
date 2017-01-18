package com.li.inspection.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.li.inspection.R;
import com.li.inspection.constant.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Created by long on 17-1-14.
 */

public class PhotoActivity_a extends BaseActivity implements SurfaceHolder.Callback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoa);
        initView();
    }
    private boolean isPreview = false;
    private SurfaceView previewSV;
    private ImageButton photoImgBtn;
    private SurfaceHolder mySurfaceHolder = null;
    private Camera myCamera = null;
    private Bitmap mBitmap = null;
    private Camera.AutoFocusCallback myAutoFocusCallback = null;
    private void initView() {
        previewSV = (SurfaceView) findViewById(R.id.previewSV);
        photoImgBtn = (ImageButton) findViewById(R.id.photoImgBtn);
        previewSV.setZOrderOnTop(false);
        mySurfaceHolder = previewSV.getHolder();
        mySurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent半透明 transparent透明
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //自动聚焦变量回调
        myAutoFocusCallback = new Camera.AutoFocusCallback() {

            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if (success) {//success表示对焦成功
                    Log.i(Constants.TAG, "myAutoFocusCallback: success...");
                    //myCamera.setOneShotPreviewCallback(null);
                } else {
                    //未对焦成功
                    Log.i(Constants.TAG, "myAutoFocusCallback: 失败了...");
                }


            }
        };
        photoImgBtn.setOnClickListener(new PhotoOnClickListener());
        photoImgBtn.setOnTouchListener(new MyOnTouchListener());
    }
    /*下面三个是SurfaceHolder.Callback创建的回调函数*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // SurfaceView启动时/初次实例化，预览界面被创建时，该方法被调用。
        myCamera = Camera.open();
        try {
            myCamera.setPreviewDisplay(mySurfaceHolder);
            Log.i(Constants.TAG, "SurfaceHolder.Callback: surfaceCreated!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            if(null != myCamera){
                myCamera.release();
                myCamera = null;
            }
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 当SurfaceView/预览界面的格式和大小发生改变时，该方法被调用
        Log.i(Constants.TAG, "SurfaceHolder.Callback:surfaceChanged!");
        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //销毁时被调用
        Log.i(Constants.TAG, "SurfaceHolder.Callback：Surface Destroyed");
        if(null != myCamera)
        {
            myCamera.setPreviewCallback(null); /*在启动PreviewCallback时这个必须在前不然退出出错。
			这里实际上注释掉也没关系*/

            myCamera.stopPreview();
            isPreview = false;
            myCamera.release();
            myCamera = null;
        }
    }
    //初始化相机
    public void initCamera(){
        if(isPreview){
            myCamera.stopPreview();
        }
        if(null != myCamera){
            Camera.Parameters myParam = myCamera.getParameters();
//            //查询屏幕的宽和高
//            WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            Log.i(Constants.TAG, "屏幕宽度："+display.getWidth()+" 屏幕高度:"+display.getHeight());

            myParam.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式

//            //查询camera支持的picturesize和previewsize
//            List<Camera.Size> pictureSizes = myParam.getSupportedPictureSizes();
//            List<Camera.Size> previewSizes = myParam.getSupportedPreviewSizes();
//            for(int i=0; i<pictureSizes.size(); i++){
//                Camera.Size size = pictureSizes.get(i);
//                Log.i(Constants.TAG, "initCamera:摄像头支持的pictureSizes: width = "+size.width+"height = "+size.height);
//                myParam.setPictureSize(size.width, size.height);
//            }
//            for(int i=0; i<previewSizes.size(); i++){
//                Camera.Size size = previewSizes.get(i);
//                Log.i(Constants.TAG, "initCamera:摄像头支持的previewSizes: width = "+size.width+"height = "+size.height);
//                myParam.setPreviewSize(size.width, size.height);
//            }


            //设置大小和方向等参数
            myParam.setPictureSize(1280, 720);
            myParam.setPreviewSize(1280, 720);
            //myParam.set("rotation", 90);
            myCamera.setDisplayOrientation(90);
            myParam.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            myCamera.setParameters(myParam);
            myCamera.startPreview();
            myCamera.autoFocus(myAutoFocusCallback);
            isPreview = true;
        }
    }
    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback()
            //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    {

        public void onShutter() {
            // TODO Auto-generated method stub
            Log.i(Constants.TAG, "myShutterCallback:onShutter...");

        }
    };
    Camera.PictureCallback myRawCallback = new Camera.PictureCallback(){
        // 拍摄的未压缩原数据的回调,可以为null
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(Constants.TAG, "myRawCallback:onPictureTaken...");
        }
    };
    Camera.PictureCallback myJpegCallback = new Camera.PictureCallback(){
        //对jpeg图像数据的回调,最重要的一个回调
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(Constants.TAG, "myJpegCallback:onPictureTaken...");
            if(null != data){
                mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
                myCamera.stopPreview();
                isPreview = false;
            }
            //设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。图片竟然不能旋转了，故这里要旋转下
//            Matrix matrix = new Matrix();
//            matrix.postRotate((float)90.0);
//            Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);

//            //旋转后rotaBitmap是960×1280.预览surfaview的大小是540×800
//            //将960×1280缩放到540×800
//            Bitmap sizeBitmap = Bitmap.createScaledBitmap(rotaBitmap, 540, 800, true);
//            Bitmap rectBitmap = Bitmap.createBitmap(sizeBitmap, 100, 200, 300, 300);//截取

            //再次进入预览
            myCamera.startPreview();
            isPreview = true;
            //预览图片
            if(null != mBitmap){
                bitmap = mBitmap;
                Intent intent = new Intent(PhotoActivity_a.this, PhotoActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    //拍照按键的监听
    public class PhotoOnClickListener implements View.OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(isPreview && myCamera!=null){
                myCamera.takePicture(myShutterCallback, null, myJpegCallback);
            }
        }
    }
    /*为了使图片按钮按下和弹起状态不同，采用过滤颜色的方法.按下的时候让图片颜色变淡*/
    public class MyOnTouchListener implements View.OnTouchListener {

        public final  float[] BT_SELECTED=new float[]
                { 2, 0, 0, 0, 2,
                        0, 2, 0, 0, 2,
                        0, 0, 2, 0, 2,
                        0, 0, 0, 1, 0 };

        public final float[] BT_NOT_SELECTED=new float[]
                { 1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0 };
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());
            }
            else if(event.getAction() == MotionEvent.ACTION_UP){
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());

            }
            return false;
        }

    }

    @Override
    public void onBackPressed(){
        //无意中按返回键时要释放内存
        // TODO Auto-generated method stub
        super.onBackPressed();
        PhotoActivity_a.this.finish();
    }
}
