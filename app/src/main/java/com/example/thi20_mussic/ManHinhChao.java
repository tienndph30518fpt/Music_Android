package com.example.thi20_mussic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ManHinhChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        ImageView imgView  = findViewById(R.id.imgManHinhChao);
        Animation animation = AnimationUtils.loadAnimation(ManHinhChao.this,R.anim.anim_music);
        imgView.startAnimation(animation);
//        Glide.with(this).load(R.mipmap.splash).into(imgView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}