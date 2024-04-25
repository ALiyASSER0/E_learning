package com.example.learning.Doctor.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.R;
import com.example.learning.databinding.ActivityCreateCourseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCourse extends AppCompatActivity implements View.OnClickListener {
ActivityCreateCourseBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(CreateCourse.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding=ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    binding.btnCreateCourse.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.btnCreateCourse.getId()){
            reciveAttribute();
        }
    }
    private void reciveAttribute() {
        String courseName=binding.editCourseName.getText().toString().trim();
        String gradeQuiz=binding.editQuizGrade.getText().toString().trim();
        String projectGrade=binding.editProjectGrade.getText().toString().trim();
        String attandanceGrade=binding.editAttendGrade.getText().toString().trim();

        validation(courseName,gradeQuiz,projectGrade,attandanceGrade);

    }

    private void validation(String courseName, String gradeQuiz, String projectGrade, String attandanceGrade) {
           binding.progeressloadingregister.setVisibility(View.VISIBLE);

        if(courseName.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editCourseName.setError(getString(R.string.req));
        }else if(gradeQuiz.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editQuizGrade.setError(getString(R.string.req));
        }else if(projectGrade.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editProjectGrade.setError(getString(R.string.req));
        }else if(attandanceGrade.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editAttendGrade.setError(getString(R.string.req));
        }

        else{
            sentDataToRealTime(courseName,gradeQuiz,projectGrade,attandanceGrade);
        }
    }

    private void sentDataToRealTime(String courseName, String gradeQuiz, String projectGrade, String attandanceGrade) {
      String courseId=ref.push().getKey();
         ref.child(Constant.COURSE).child(courseId).setValue(new com.example.learning.Moduls.CreateCourse(courseName,gradeQuiz,projectGrade,attandanceGrade,courseId,auth.getCurrentUser().getUid(),gradeQuiz))
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         binding.progeressloadingregister.setVisibility(View.GONE);
                       Intent intent=new Intent(CreateCourse.this,HomeDoctor.class);
                       intent.putExtra("gradeQuiz",gradeQuiz);
                       startActivity(intent);
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(CreateCourse.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });




    }


}