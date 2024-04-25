package com.example.learning.Student.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.Student.Manage.PageStudent;
import com.example.learning.databinding.ActivityFinishQuizBinding;

public class AnimationFinishQuiz extends AppCompatActivity {
ActivityFinishQuizBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityFinishQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(AnimationFinishQuiz.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding.animationView.animate();
        CreateCourse objCourse= (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AnimationFinishQuiz.this, PageStudent.class);
                intent.putExtra("ObjectCourse",objCourse);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}