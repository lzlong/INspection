package com.li.inspection.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

public class DrawImageView extends ImageView{

	public DrawImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
	}
    int width;
    int height;
	Paint paint = new Paint();
	{
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2.5f);//设置线宽
		paint.setAlpha(100);
	};
    private Paint mAreaPaint = new Paint();
    {
        mAreaPaint.setAntiAlias(true);
        mAreaPaint.setColor(Color.BLACK);
        mAreaPaint.setStyle(Style.FILL);
//        mAreaPaint.setStrokeWidth(2.5f);//设置线宽
        mAreaPaint.setAlpha(100);
    };
	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawRect(new Rect(375, 30, 705, 1500), paint);//绘制矩形
        //绘制四周阴影区域
        canvas.drawRect(0, 0, width, 30, mAreaPaint);
        canvas.drawRect(0, 1500, width, height, mAreaPaint);
        canvas.drawRect(0, 30, 375, 1500, mAreaPaint);
        canvas.drawRect(705, 30, width, 1500, mAreaPaint);
    }
	
	


	

}
