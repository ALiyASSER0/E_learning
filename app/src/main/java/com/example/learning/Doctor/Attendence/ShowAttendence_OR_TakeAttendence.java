package com.example.learning.Doctor.Attendence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learning.R;
import com.example.learning.databinding.ActivityShowAttendenceOrTakeAttendenceBinding;

public class ShowAttendence_OR_TakeAttendence extends AppCompatActivity {
ActivityShowAttendenceOrTakeAttendenceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityShowAttendenceOrTakeAttendenceBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowAttendence_OR_TakeAttendence.this, R.color.purple_500));
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        String idCourse=getIntent().getStringExtra("idCourse");
        binding.animationView.animate();
        binding.animationView2.animate();
        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowAttendence_OR_TakeAttendence.this, TakeAttendence.class);
                intent.putExtra("idCourse",idCourse);
                startActivity(intent);

            }
        });
        binding.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowAttendence_OR_TakeAttendence.this, ShowAttendence.class);
                intent.putExtra("idCourse",idCourse);
                startActivity(intent);

            }
        });

    }
}