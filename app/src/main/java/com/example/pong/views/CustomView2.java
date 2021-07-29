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


public class CustomView2 extends View {
    Rect mrect,srect,crect;
    Paint mpaint,spaint,fpaint;
    Paint tpaint,t2paint;
    private Bitmap bmp;
    public static int x=500;
    public static int y=100;
    public static int xspeed=5;
    public static int yspeed=5;
    private int spriteWidth=0;
    private int check=0,cscore=0,timecheck=0,slidelenght=300;
    private int score=0,fs=0,rand,drand,w=5,bot,boosterdown=100,boosterlocation=-10,boostercheck=1;
    private float botstart=0,botmove=6;
    private int createbooster=0;
    MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer3;
    int time=10000;
    public CustomView2(Context context) {
        super(context);
        mediaPlayer =MediaPlayer.create(context,R.raw.beep);
        mediaPlayer1 =MediaPlayer.create(context,R.raw.beep1);
        mediaPlayer3 =MediaPlayer.create(context,R.raw.abc);
        init(null);
    }
    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    public void waits()
    {
        new CountDownTimer(5000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                w--;
                timecheck=1;
                postInvalidate();
            }

            @Override
            public void onFinish() {
                timecheck=2;
                postInvalidate();
                cancel();
            }
        }.start();

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
                if(createbooster==1)
                boosterdown+=3;
                postInvalidate();
                fs++;
            }
            @Override
            public void onFinish()
            {
                if(check==0) {
                    if(xspeed>0)
                    {
                        xspeed++;
                    }
                    else
                    {
                        xspeed--;
                    }
                    if(yspeed>0) {
                        yspeed++;
                    }
                    else {
                        yspeed--;
                    }
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
        crect =new Rect();
        srect = new Rect();
        tpaint = new Paint();
        t2paint = new Paint();
        fpaint = new Paint();
        spaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        spaint.setAntiAlias(true);
        spaint.setColor(Color.RED);
        mpaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLUE);
        tpaint.setTextSize(30);
        tpaint.setColor(Color.RED);
        t2paint.setTextSize(32);
        t2paint.setColor(Color.WHITE);
        fpaint.setTextSize(40);
        fpaint.setColor(Color.BLACK);
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
      /*  if(y<90) {
            yspeed *= -1;
            mediaPlayer.start();
        }*/
        if((y<87)&&(x-10>=crect.left&&x<=crect.left+300))
        {
            yspeed*=-1;
            if(check==0) {
                cscore++;
            }
            mediaPlayer1.start();
        }
        if(y>=getHeight()-60||y<80) {
            check=1;
            if(mediaPlayer3!=null)
                mediaPlayer3.start();
            SharedPreferences getscore = getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
            int scr = getscore.getInt("hscore",0);
            if(scr<score)
            {
                SharedPreferences.Editor editor = getscore.edit();
                editor.putInt("hscore",score);
                editor.apply();
                scr=score;
            }
            tpaint.setColor(Color.WHITE);
            tpaint.setTextSize(30);
            srect.left=getWidth()-20;
            srect.top = getHeight()/35;
            srect.right=20;
            srect.bottom = (getHeight()/7)*2;
            canvas.drawRect(srect,spaint);
            canvas.drawText("Game Over!",getWidth()/2-80,getHeight()/35+200,t2paint);
            canvas.drawText("Highest Score:"+scr,getWidth()/2-100,getHeight()/35+150,t2paint);
            if(cscore<score)
            canvas.drawText("You Win!",getWidth()/2-80,getHeight()/35+250,t2paint);
            else if(cscore>score)
                canvas.drawText("You Loose!",getWidth()/2-90,getHeight()/35+250,t2paint);
            else
                canvas.drawText("Tie!",getWidth()/2-20,getHeight()/35+250,t2paint);
            if(timecheck==1)
                canvas.drawText("Wait:"+w,getWidth()/2-30,getHeight()/2,fpaint);
            if(timecheck==0)
            {
                waits();
            }
            if(timecheck==2)
            {
                canvas.drawText("Slide To Restart",getWidth()/2-150,getHeight()/2,fpaint);
            }

        }
        if((y>=(this.getHeight()-80))&&(x-10>=mrect.left&&x<=mrect.left+slidelenght))
        {
            yspeed*=-1;
            if(check==0&&fs>40) {
                fs=0;
                score++;
            }
            mediaPlayer1.start();
        }
        if(mrect.left==0){
            mrect.left=getWidth()/2-150;
        }
        mrect.top = getHeight()-40;
        mrect.right=mrect.left+slidelenght;
        mrect.bottom=mrect.top+100;

        if(createbooster==0&&boostercheck==1)
        {
            drand = (int)(Math.random()*(getWidth()-50))+30;
            boosterlocation=drand;
            createbooster=1;
            boostercheck=0;
            boosterdown=100;
        }
        if(boostercheck==0&&createbooster==1)
            canvas.drawCircle(boosterlocation,boosterdown,30,mpaint);
        if((boosterlocation>=mrect.left)&&(boosterlocation<=mrect.left+300)&&boosterdown>=(this.getHeight()-80))
        {
            boostercheck=0;
            createbooster=0;
            boosterdown=100;
            powerbooster();
        }
       else if(boosterdown>=this.getHeight()-80)
        {
            createbooster=0;
            boostercheck=1;
        }
        if(canvas!=null)
            canvas.drawRect(mrect,mpaint);
        canvas.drawBitmap(bmp,x,y,mpaint);
      if(botstart==0)
        {
            bot =x;
            botstart=1;
        }
        if(xspeed>0&&botmove<0||xspeed<0&&botmove>0)
            botmove*=-1;
        bot+=botmove;
        if(bot>150&&bot<getWidth()-150)
        crect.left =bot-150;
        crect.top = 40;
        crect.right=crect.left+300;
        crect.bottom=crect.top+40;
        canvas.drawRect(crect,spaint);
        canvas.drawText("Your Score:" + score, getWidth()/3+getWidth()/10-20, getHeight()/12, tpaint);
        canvas.drawText("Compter Score:" + cscore, getWidth()/3+getWidth()/10-40, getHeight()/9, tpaint);
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
                if(timecheck!=1) {
                    float X = event.getX();
                    if (X>slidelenght/2&&X < (getWidth() -slidelenght/2)) {
                        if (X >= mrect.left && X <= mrect.right) {
                            mrect.left = (int) X-slidelenght/2;
                        }
                    }
                    postInvalidate();
                    if (check == 1 && timecheck == 2) {
                        mediaPlayer3.pause();
                        timecheck = 0;
                        y = 100;
                        xspeed = 5;
                        yspeed = 5;
                        rand = (int) (Math.random() * (getWidth() - 40)) + 10;
                        x = rand;
                        start();
                        check = 0;
                        score = 0;
                        tpaint.setColor(Color.RED);
                        w = 5;
                        botmove=5;
                        botstart=0;
                        cscore=0;
                        createbooster=0;
                        boosterdown=100;
                    }
                    postInvalidate();
                }
                return true;
            }

        }
        return value;
    }
    public void powerbooster()
    {
        new CountDownTimer(5000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
               slidelenght=400;
                postInvalidate();
            }

            @Override
            public void onFinish() {
                slidelenght=300;
                createbooster=0;
                boostercheck=1;
                postInvalidate();
                cancel();
            }
        }.start();

    }
}