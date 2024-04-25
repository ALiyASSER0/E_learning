package com.example.learning.Doctor.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.learning.CommonBetweenThem.Chat;
import com.example.learning.Doctor.Attendence.ShowAttendence_OR_TakeAttendence;
import com.example.learning.Doctor.Grades.ShowAllGrages;
import com.example.learning.Doctor.Material.ShowMaterial;
import com.example.learning.Doctor.Quiz.AnimationNoQuizAvailable;
import com.example.learning.Doctor.Quiz.Quiz;
import com.example.learning.Moduls.CreateCourse;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.R;
import com.example.learning.databinding.ActivityPageDoctorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PageDoctor extends AppCompatActivity implements View.OnClickListener {
ActivityPageDoctorBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

    String idCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(PageDoctor.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding=ActivityPageDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.attendance.animate();
     binding.grade.animate();
     binding.quiz.animate();
     binding.upload.animate();
     binding.chat.animate();
        idCourse=getIntent().getStringExtra("idCourse");
    binding.btnAddMember.setText(idCourse);
    binding.btnAddMember.setOnClickListener(this::onClick);
    binding.btnOpenTakeAttendance.setOnClickListener(this::onClick);
binding.openQuzeToMakeItInCourseHomeDocotr.setOnClickListener(this::onClick);
binding.showAllGradeInCourseHomeDocotr.setOnClickListener(this::onClick);
binding.openToUploadNewMatrialInCourseHomeDocotr.setOnClickListener(this::onClick);
binding.chat.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.btnOpenTakeAttendance.getId()){
           Intent intent=new Intent(PageDoctor.this, ShowAttendence_OR_TakeAttendence.class);
           intent.putExtra("idCourse",idCourse);
            startActivity(intent);
        }else if(v.getId()==binding.openQuzeToMakeItInCourseHomeDocotr.getId()){
         ref.child(Constant.COURSE).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
               CreateCourse c=snapshot.getValue(CreateCourse.class);
                 if(c.getGradeAvailable().equals("0")){
                     Intent intent=new Intent(PageDoctor.this, AnimationNoQuizAvailable.class);
                     intent.putExtra("idCourse",idCourse);
                     startActivity(intent);
                 }else{
                     Intent intent=new Intent(PageDoctor.this, Quiz.class);
                     intent.putExtra("idCourse",idCourse);
                     startActivity(intent);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
        }else if(v.getId()==binding.showAllGradeInCourseHomeDocotr.getId()){
            Intent intent=new Intent(PageDoctor.this, ShowAllGrages.class);
            intent.putExtra("idCourse",idCourse);
            startActivity(intent);
        }else if(v.getId()==binding.openToUploadNewMatrialInCourseHomeDocotr.getId()){
            Intent intent=new Intent(PageDoctor.this, ShowMaterial.class);
            intent.putExtra("idCourse",idCourse);
            startActivity(intent);
        }else if(v.getId()==binding.chat.getId()){
            Intent intent=new Intent(PageDoctor.this, Chat.class);
            intent.putExtra("idCourse",idCourse);
            startActivity(intent);
        }else if(v.getId()==binding.btnAddMember.getId()){
            ClipboardManager cm = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText( binding.btnAddMember.getText());
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        }
    }
}