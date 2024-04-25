package com.example.learning.Student.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.databinding.ActivitySplashGotoQuizBinding;

public class SplashGotoQuiz extends AppCompatActivity {
ActivitySplashGotoQuizBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashGotoQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashGotoQuiz.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding.animationView.animate();
        CreateCourse objCourse= (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
        String posittion=getIntent().getStringExtra("position");
        String type=getIntent().getStringExtra("type");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashGotoQuiz.this, Quiz.class);
                intent.putExtra("ObjectCourse",objCourse);
                intent.putExtra("position",posittion);
                intent.putExtra("type",type);
                startActivity(intent);
                finish();
            }
        },3500);
    }
}