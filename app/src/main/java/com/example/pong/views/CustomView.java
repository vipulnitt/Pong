package com.example.pong.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.View;
import androidx.annotation.Nullable;

import com.example.pong.R;


public class CustomView extends View {
    Rect mrect;
    Paint mpaint;
    Paint tpaint;
    private Bitmap bmp;
    public static int x=500;
    public static int y=10;
    public static int xspeed=5;
    public static int yspeed=5;
    private int spriteWidth=0;
    private int check=0;
    private int score=0,fs=0,rand;
    MediaPlayer mediaPlayer;
    int time=10000;
    public CustomView(Context context) {
        super(context);
        mediaPlayer =MediaPlayer.create(context,R.raw.beep);
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
        new CountDownTimer(time, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(check==1) {
                    cancel();
                }
             x+=xspeed;
             y+=yspeed;
                postInvalidate();
             fs++;
            }
            @Override
            public void onFinish()
            {
                if(check==0) {
                    if(xspeed>0)
                        xspeed++;
                    else
                        xspeed--;
                    if(yspeed>0)
                        yspeed++;
                    else
                        yspeed--;
                    start();
                }
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
        tpaint.setTextSize(50);
        tpaint.setColor(Color.BLUE);
        if(check==0) {
            rand = (int)(Math.random()*(getWidth()-10))+10;
            x=rand;
            start();
        }
        if(set==null)
     {
         return;
     }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        if (x < 0 || x + spriteWidth >= canvas.getWidth()){
            xspeed *= -1;
            mediaPlayer.start();
    }
        if(y<0) {
            yspeed *= -1;
            mediaPlayer.start();
        }
        if(y>=getHeight()-60) {
            check=1;
            SharedPreferences getscore = getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
            int scr = getscore.getInt("hscore",0);
            if(scr<score)
            {
                SharedPreferences.Editor editor = getscore.edit();
                editor.putInt("hscore",score);
                editor.apply();
                scr=score;
            }
            canvas.drawText("Game Over Move Slider To Restart!",getWidth()/6+getWidth()/13,getHeight()/10,tpaint);
            canvas.drawText("Highest Score:"+scr,getWidth()/6+getWidth()/13,getHeight()/6,tpaint);

        }
        if((y>=(this.getHeight()-80))&&(x>=mrect.left&&x<=mrect.left+300))
        {
            yspeed*=-1;
            if(check==0&&fs>40) {
                fs=0;
                score++;
            }
        }
        if(mrect.left==0){
            mrect.left=getWidth()/2-150;
        }
        mrect.top = getHeight()-40;
        mrect.right=mrect.left+300;
        mrect.bottom=mrect.top+100;
        if(canvas!=null)
        canvas.drawRect(mrect,mpaint);
        canvas.drawBitmap(bmp,x,y,mpaint);
            canvas.drawText("Score:" + score, getWidth()/3+getWidth()/10, getHeight()/14, tpaint);
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
                float X =event.getX();
                if(X<(getWidth()-295))
                mrect.left= (int) X;
                postInvalidate();
                if(check==1)
                {
                    y=10;
                    x=500;
                    xspeed=5;
                    yspeed=5;
                    rand = (int)(Math.random()*(getWidth()-10))+10;
                    x=rand;
                    start();
                    check=0;
                    score=0;
                    }
                postInvalidate();
                return true;
            }

        }
        return value;
    }

}