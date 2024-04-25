package com.example.learning.Student.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.learning.databinding.ActivityPageStudentInfBinding;
public class PageStudentInf extends AppCompatActivity {
ActivityPageStudentInfBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding=ActivityPageStudentInfBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(PageStudentInf.this, R.color.purple_500));
        getSupportActionBar().hide();
    setContentView(binding.getRoot());
        CreateCourse objCourse= (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
        binding.animationView2.animate();
        ref.child(Constant.COURSE).child(objCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CreateCourse c=snapshot.getValue(CreateCourse.class);
                binding.nameCourse.setText("course name:  "+c.getCourseName());
                binding.attentenceCourse.setText("attendence grade:  "+c.getAttendanceGrade());
                binding.gradeQuiz.setText("grade quiz:  "+c.getGradeQuiz());
                binding.projectGride.setText("project grade:  "+c.getProjectGrade());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref.child(Constant.USERS).child(objCourse.getDoctorId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignOut m=snapshot.getValue(SignOut.class);
                binding.doctorCourse.setText("doctor name:  "+m.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}