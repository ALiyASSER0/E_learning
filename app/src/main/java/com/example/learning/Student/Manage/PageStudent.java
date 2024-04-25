package com.example.learning.Student.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.CommonBetweenThem.Chat;
import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;
import com.example.learning.Student.Grades.GrideAllQuizes;
import com.example.learning.Student.Material.Material;
import com.example.learning.Student.Quiz.Question_Or_Answer2;
import com.example.learning.databinding.ActivityPageStudentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PageStudent extends AppCompatActivity {
ActivityPageStudentBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(PageStudent.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding=ActivityPageStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CreateCourse ObjCourse = (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");

     binding.Material.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(PageStudent.this, Material.class);
             intent.putExtra("idCourse",ObjCourse.getCourseId());
             startActivity(intent);
         }
     });

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageStudent.this, Chat.class);
                intent.putExtra("idCourse",ObjCourse.getCourseId());
                startActivity(intent);
            }
        });



        binding.btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageStudent.this, GrideAllQuizes.class);
                intent.putExtra("ObjectCourse",ObjCourse);
                startActivity(intent);
            }
        });

        binding.LayQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageStudent.this, Question_Or_Answer2.class);
                intent.putExtra("ObjectCourse",ObjCourse);
                startActivity(intent);
            }
        });

        binding.Lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageStudent.this,PageStudentInf.class);
                intent.putExtra("ObjectCourse",ObjCourse);
                startActivity(intent);
            }
        });

      ref.child(Constant.USERS).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              SignOut m=snapshot.getValue(SignOut.class);
              binding.nameStuentInStudentpage.setText("hello "+m.getName());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });





     binding.nameSubject.setText(ObjCourse.getCourseName());
    binding.btnAttend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String editAttend=binding.editAttend.getText().toString().trim();
            validation(editAttend,ObjCourse);
        }
    });


    }

public void validation(String editAttend,CreateCourse ObjCourse){
if(editAttend.isEmpty()){
    binding.editAttend.setError(getString(R.string.req));
}else{
    getData(editAttend,ObjCourse);
}
}
public void getData(String editAttend,CreateCourse ObjCourse){

    ref.child(Constant.ATTENDANCE).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.hasChild(ObjCourse.getCourseId())){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        for(DataSnapshot snapshot2:snapshot1.getChildren()) {
                            for (DataSnapshot snapshot3 : snapshot2.getChildren()){
                                if (editAttend.equals(snapshot3.getKey())) {
                                    if (snapshot3.hasChild(auth.getUid())) {
                                        Toast.makeText(PageStudent.this, "you take attendance already", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ref.child(Constant.ATTENDANCE).child(ObjCourse.getCourseId()).child(snapshot2.getKey()).child(snapshot3.getKey())
                                                .child(auth.getUid()).setValue(auth.getUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        ref.child(Constant.COURSES_OF_STUDENT).child(ObjCourse.getCourseId()).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                CreateCourse c = snapshot.getValue(CreateCourse.class);
                                                                int attendanceGrade = Integer.parseInt(c.getAttendanceGrade());
                                                                attendanceGrade = attendanceGrade + 1;
                                                                ref.child(Constant.COURSES_OF_STUDENT).child(ObjCourse.getCourseId()).child(auth.getUid()).setValue(new CreateCourse(c.getCourseName(), c.getGradeQuiz(), c.getProjectGrade(), attendanceGrade + "", c.getCourseId(), c.getDoctorId(),c.getGradeAvailable()));
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(PageStudent.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }

                                } else {
                                    Toast.makeText(PageStudent.this, "code not found", Toast.LENGTH_SHORT).show();
                                }
                        }
                        }

                    }

            }else{
                Toast.makeText(PageStudent.this, "code not found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}



}