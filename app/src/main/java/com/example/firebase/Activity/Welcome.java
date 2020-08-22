package com.example.firebase.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.firebase.R;

public class Welcome extends AppCompatActivity {
    private static  int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ImageView imageView = findViewById(R.id.logowelcome);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


//        Thread timer = new Thread(){
//
//            @Override
//            public void run() {
//
//                try {
//                    sleep(4000);
//                    Intent intent = new Intent(Welcome.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    super.run();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        };
//
//        timer.start();
    }
}
