package com.example.learning.Doctor.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.learning.Doctor.Manage.PageDoctor;
import com.example.learning.R;
import com.example.learning.databinding.ActivityNoQuizAvailableBinding;

public class AnimationNoQuizAvailable extends AppCompatActivity {
ActivityNoQuizAvailableBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityNoQuizAvailableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(AnimationNoQuizAvailable.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding.animationView2.animate();
        String objCourse=getIntent().getStringExtra("idCourse");

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(AnimationNoQuizAvailable.this, PageDoctor.class);
//                intent.putExtra("idCourse",objCourse);
//                startActivity(intent);
//                finish();
//            }
//        },4000);
    }
}