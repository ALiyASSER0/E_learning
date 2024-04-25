package com.example.learning.Doctor.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.R;
import com.example.learning.Moduls.ModelQuiz;
import com.example.learning.databinding.ActivityQuiz2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.learning.Moduls.CreateCourse;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    private ActivityQuiz2Binding binding;
    private ArrayList<ModelQuiz> list = new ArrayList<>();
    private int rightAnswer = 0;
    String numOfQuiz="";
     String gradeQuiz;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuiz2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(Quiz.this, R.color.purple_500));
        getSupportActionBar().hide();
        String idCourse=getIntent().getStringExtra("idCourse");
        binding.editAnswer5.setVisibility(View.VISIBLE);
        binding.textView3.setVisibility(View.VISIBLE);
           ref.child(Constant.COURSE).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   CreateCourse c=snapshot.getValue(CreateCourse.class);
                   gradeQuiz=c.getGradeAvailable();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });


        binding.checkBoxOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rightAnswer = 1;
                    binding.checkBoxOne.setChecked(true);
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                }
            }
        });
        binding.checkBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rightAnswer = 2;
                    binding.checkBoxTwo.setChecked(true);
                    binding.checkBoxOne.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                }
            }
        });
        binding.checkBoxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rightAnswer = 3;
                    binding.checkBoxThree.setChecked(true);
                    binding.checkBoxOne.setChecked(false);
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                }
            }
        });
        binding.checkBoxFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rightAnswer = 4;
                    binding.checkBoxFour.setChecked(true);
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxOne.setChecked(false);
                }
            }
        });


        binding.btnUploadQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String degree=binding.editAnswer5.getText().toString().trim();
         if(!degree.isEmpty()){
             double degreee=Double.parseDouble(degree);
             if(Double.parseDouble(degree)>Double.parseDouble(gradeQuiz)){
                 Toast.makeText(Quiz.this, "the max degree:"+gradeQuiz, Toast.LENGTH_SHORT).show();
             }else{
                 if(recive_Attribute_And_Validation(0)){

                     ref.child(Constant.QUIZ).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             for(DataSnapshot snapshot1:snapshot.getChildren()){
                                 numOfQuiz=snapshot1.getKey();
                             }
                             char x='0';
                             if(!numOfQuiz.isEmpty()) {
                                 x=numOfQuiz.charAt(5);
                             }
                             int a = x - '0';
                             int increaseA=a+1;
                             Log.e("qw",a+"");
                             ref.child(Constant.QUIZ).child(idCourse).child("Quiz "+increaseA).child(Constant.DEGREE_OF_QUIZ).setValue(degreee);
                             ref.child(Constant.QUIZ).child(idCourse).child("Quiz "+increaseA).child(Constant.QUESTIONS).setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     ref.child(Constant.COURSE).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             CreateCourse c=snapshot.getValue(CreateCourse.class);
                                             int newGradeAvailable=Integer.parseInt(c.getGradeAvailable())-Integer.parseInt(binding.editAnswer5.getText().toString().trim());
                                             ref.child(Constant.COURSE).child(idCourse).setValue(new CreateCourse(c.getCourseName(),c.getGradeQuiz(),c.getProjectGrade(),c.getAttendanceGrade(),c.getCourseId(),c.getDoctorId(),newGradeAvailable+""));

                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });
                                     int newgradeQuiz=Integer.parseInt(gradeQuiz)-Integer.parseInt(binding.editAnswer5.getText().toString().trim());
                                     gradeQuiz=newgradeQuiz+"";
                                     goToFinish(idCourse);

                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(Quiz.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });


                             list.clear();





                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });


                 }
             }

         }else{
             binding.editAnswer5.setError(getString(R.string.req));
         }


            }
        });


        binding.buttonMakeNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recive_Attribute_And_Validation(1);
            }
        });

    }


    private boolean recive_Attribute_And_Validation(int type){
        String Question = binding.editQuestion.getText().toString().trim();
        String Choose1 = binding.editAnswer1.getText().toString().trim();
        String Choose2 = binding.editAnswer2.getText().toString().trim();
        String Choose3 = binding.editAnswer3.getText().toString().trim();
        String Choose4 = binding.editAnswer4.getText().toString().trim();
        if(validation(Question, Choose1, Choose2, Choose3, Choose4,type)){
            return true;
        }else{
            return false;
        }
    }


    private boolean validation(String question, String choose1, String choose2, String choose3, String choose4,int type) {
        if (question.equals("")) {
            binding.editQuestion.setError(getString(R.string.required));
       return false;
        } else if (choose1.equals("")) {
            binding.editAnswer1.setError(getString(R.string.required));
            return false;
        } else if (choose2.equals("")) {
            binding.editAnswer2.setError(getString(R.string.required));
            return false;
        } else if (choose3.equals("")) {
            binding.editAnswer3.setError(getString(R.string.required));
            return false;
        } else if (choose4.equals("")) {
            binding.editAnswer4.setError(getString(R.string.required));
            return false;
        } else if (!(binding.checkBoxOne.isChecked()||binding.checkBoxTwo.isChecked()||binding.checkBoxThree.isChecked()||binding.checkBoxFour.isChecked())) {
            Toast.makeText(this, "Please Select Right Answer", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(type==1){
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }
            list.add(new ModelQuiz(question, choose1, choose2, choose3, choose4, rightAnswer));
          if(!binding.editAnswer5.getText().toString().trim().isEmpty()){
              binding.editAnswer5.setVisibility(View.GONE);
              binding.textView3.setVisibility(View.GONE);
          }
            binding.editQuestion.setText("");
            binding.editAnswer1.setText("");
            binding.editAnswer2.setText("");
            binding.editAnswer3.setText("");
            binding.editAnswer4.setText("");
            binding.checkBoxOne.setChecked(false);
            binding.checkBoxTwo.setChecked(false);
            binding.checkBoxThree.setChecked(false);
            binding.checkBoxFour.setChecked(false);
            return true;
        }


    }

    private void goToFinish(String objCourse){
        Intent intent=new Intent(Quiz.this, AnimationFinishQuiz.class);
        intent.putExtra("idCourse",objCourse);
        startActivity(intent);
        finish();
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}

