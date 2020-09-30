package com.example.ngo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Splash_activity extends AppCompatActivity{
    public static int SPLASH_SCREEN=5000;
    ImageView image;
    TextView text1,text2;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Hooks
        image=findViewById(R.id.splash_image);
        text1=findViewById(R.id.splash_text1);
        text2=findViewById(R.id.splash_text2);

        image.setAnimation(topAnim);
        text1.setAnimation(bottomAnim);
        text2.setAnimation(bottomAnim);
      new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Call next screen
                Intent intent=new Intent(Splash_activity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}