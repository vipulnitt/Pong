package com.example.pong.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.Nullable;

import com.example.pong.R;


public class CustomView extends View {
    Rect mrect;
    Paint mpaint;
    Paint tpaint;
    private Bitmap bmp;
    public static int x=10;
    public static int y=10;
    public static int xspeed=10;
    public static int yspeed=10;
    private int spriteWidth=0;
    private int check=0;
    private int score=0;

    int time=3000;
    public CustomView(Context context) {
        super(context);
        init(null);
    }
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public  void start() {
        new CountDownTimer(time, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(check==1)
                    cancel();
             x+=xspeed;
             y+=yspeed;
             postInvalidate();
            }
            @Override
            public void onFinish()
            {
                if(check==0)
                 start();
                else cancel();
            }
        }.start();
    }

    private void init(@Nullable AttributeSet set)
    {
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        spriteWidth = bmp.getWidth();
        mrect =new Rect();
        tpaint = new Paint();
        mpaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLUE);
        tpaint.setTextSize(100);
        tpaint.setColor(Color.BLUE);
        start();
        if(set==null)
     {
         return;
     }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        if(x<0||x+spriteWidth>=canvas.getWidth())
            xspeed*=-1;
        if(y<0) {
            yspeed *= -1;
        }
        if((y>this.getHeight()-240)&&(x>mrect.left&&x<mrect.left+150))
        {
            yspeed*=-1;
            score++;
        }
        if(y>=getHeight()) {
            check=1;
            canvas.drawText("Game Over",400,100,tpaint);
        }
        if(mrect.left==0){
            mrect.left=getWidth()/2-150;
        }
        mrect.top = getHeight()-40;
        mrect.right=mrect.left+400;
        mrect.bottom=mrect.top+100;
        if(canvas!=null)
        canvas.drawRect(mrect,mpaint);
        canvas.drawBitmap(bmp,x,y,mpaint);
        canvas.drawText("Score:"+score,450,200,tpaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value =super.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                float x =event.getX();
                if(x<(getWidth()-295))
                mrect.left= (int) x;
                postInvalidate();
                return true;
            }
        }
        return value;
    }
}