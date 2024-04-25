package com.example.learning.Student.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.databinding.ActivityQuestionOrAnswer2Binding;

public class Question_Or_Answer2 extends AppCompatActivity {
ActivityQuestionOrAnswer2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuestionOrAnswer2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(Question_Or_Answer2.this, R.color.purple_500));
        getSupportActionBar().hide();
        CreateCourse ObjCourse = (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
      binding.animationView.animate();
      binding.animationView2.animate();
        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Question_Or_Answer2.this, RecycleAllQuizes.class);
                intent.putExtra("ObjectCourse",ObjCourse);
                intent.putExtra("type","0");
                startActivity(intent);
            }
        });
    binding.card2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Question_Or_Answer2.this, RecycleAllQuizes.class);
            intent.putExtra("ObjectCourse",ObjCourse);
            intent.putExtra("type","1");
            startActivity(intent);
        }
    });


    }
}