package com.example.learning.Student.Grades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.Moduls.ModelAnswerQuiz;
import com.example.learning.R;
import com.example.learning.databinding.ActivityGradeQuizOnlyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GradeQuizOnly extends AppCompatActivity {
ActivityGradeQuizOnlyBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGradeQuizOnlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(GradeQuizOnly.this, R.color.purple_500));
        getSupportActionBar().hide();

        CreateCourse ObjCourse = (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
       String position=getIntent().getStringExtra("position");
    ref.child(Constant.QUIZ_ANSWER).child(ObjCourse.getCourseId()).child(position).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelAnswerQuiz z=snapshot.getValue(ModelAnswerQuiz.class);
                binding.totalGride.setText("total grade of Quiz: "+z.getTotalGrades());
                binding.correctAswer.setText("correct answer: "+z.getQuizGrideCorrect());
                binding.wrongAnswer.setText("wrong answer:  "+z.getQuizGrideWrong());
             if(Double.parseDouble(z.getQuizGrideCorrect())>Double.parseDouble(z.getQuizGrideWrong())){
                 binding.animationView2.setVisibility(View.VISIBLE);
                 binding.animationView2.animate();
             }else{
                 binding.animationView3.setVisibility(View.VISIBLE);
                 binding.animationView3.animate();
             }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



    }
}