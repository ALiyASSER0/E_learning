package com.example.learning.Student.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.Moduls.ModelAnswerQuiz;
import com.example.learning.Moduls.ModelQuiz;
import com.example.learning.R;
import com.example.learning.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
ActivityQuizBinding binding;
FirebaseAuth auth=FirebaseAuth.getInstance();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
ArrayList<ModelQuiz>list=new ArrayList<>();
private int rightAnswer = 0;
private int grade = 0;
private  int  position = 0;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding= ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    getWindow().setStatusBarColor(ContextCompat.getColor(Quiz.this, R.color.purple_500));
    getSupportActionBar().hide();
    binding.animationView.animate();
    CreateCourse objCourse= (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
   String posittion=getIntent().getStringExtra("position");
    String type=getIntent().getStringExtra("type");

    ref.child(Constant.QUIZ).child(objCourse.getCourseId()).child(posittion).child(Constant.QUESTIONS).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot snapshot1:snapshot.getChildren()){
                  list.add(snapshot1.getValue(ModelQuiz.class));

            }
        if(type.equals("0")){
            init();
        }else if(type.equals("1")){
            init2();
        }



        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    if(type.equals("0")){
        showQuiz(objCourse,posittion);
    }else if(type.equals("1")){
        answerQuiz(objCourse);

    }



    }



    private void answerQuiz(CreateCourse objCourse){
      binding.buttonConfermToSolveNextQuestion.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (position == (list.size() - 1)) {
                  goToFinish(objCourse);
              }else{
                  position+=1;
                  resetAnswer();
                  init2();
              }


          }
      });

    }




    private void showQuiz(CreateCourse objCourse, String posittion){
        binding.chooseOneInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightAnswer = 1;
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.bottomNavigationTextColor));
                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));
            }
        });
        binding.chooseTwoInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 2;
                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

            }
        });
        binding.chooseThreeInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 3;
                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));
            }
        });
        binding.chooseFoureInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 4;
                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));
            }
        });

        binding.buttonConfermToSolveNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              binding.progeressloadingregister.setVisibility(View.VISIBLE);
                if(rightAnswer==0){
                    binding.progeressloadingregister.setVisibility(View.GONE);
                    Toast.makeText(Quiz.this, "please select answer", Toast.LENGTH_SHORT).show();

                }else  if (list.get(position).getRightAnswer() == rightAnswer) {
                    grade++;
                }
               if(!(rightAnswer==0)){
                   if ((position == (list.size() - 1))) {
                       binding.progeressloadingregister.setVisibility(View.GONE);
                       sentDataToFireBase(objCourse,posittion);
                   } else {
                       binding.progeressloadingregister.setVisibility(View.GONE);
                       position++;
                       init();
                   }
               }

                resetAnswer();
            }
        });

    }


    private void sentDataToFireBase(CreateCourse objCourse, String posittion) {
        binding.progeressloadingregister.setVisibility(View.VISIBLE);

        ref.child(Constant.QUIZ).child(objCourse.getCourseId()).child(posittion).child(Constant.DEGREE_OF_QUIZ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                double degreeOfQuiz=Double.parseDouble( snapshot.getValue()+"")/(list.size()*1.0);
                double correctAnswer=grade*degreeOfQuiz;
                double wrongAnswer=(list.size()-grade)*degreeOfQuiz;

                ref.child(Constant.QUIZ_ANSWER).child(objCourse.getCourseId())
                        .child(posittion).child(auth.getUid()).setValue(new ModelAnswerQuiz(snapshot.getValue()+"",correctAnswer+"",wrongAnswer+"")).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ref.child(Constant.COURSES_OF_STUDENT).child(objCourse.getCourseId())
                                        .child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                CreateCourse c=snapshot.getValue(CreateCourse.class);
                                                double Gride= Double.parseDouble(c.getGradeQuiz());
                                                double sumGride=Gride+correctAnswer;
                                                ref.child(Constant.COURSES_OF_STUDENT).child(objCourse.getCourseId())
                                                        .child(auth.getUid()).setValue(new CreateCourse(c.getCourseName(),sumGride+"",c.getProjectGrade(),c.getAttendanceGrade(),c.getCourseId(),c.getDoctorId(),c.getGradeAvailable()))
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                binding.progeressloadingregister.setVisibility(View.GONE);

                                                                goToFinish(objCourse);
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Quiz.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });








                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Quiz.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//
//
//
//        ref.child(Constant.QUIZ_ANSWER).child(objCourse.getCourseId())
//              .child(posittion).child(auth.getUid()).setValue(new ModelAnswerQuiz(list.size()+"",grade+"",(list.size()-grade)+""))
//              .addOnSuccessListener(new OnSuccessListener<Void>() {
//                  @Override
//                  public void onSuccess(Void unused) {
//                      ref.child(Constant.COURSES_OF_STUDENT).child(objCourse.getCourseId())
//                              .child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                                  @Override
//                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                          CreateCourse c=snapshot.getValue(CreateCourse.class);
//                                         int Gride= Integer.parseInt(c.getGradeQuiz());
//                                         int sumGride=Gride+grade;
//                                          ref.child(Constant.COURSES_OF_STUDENT).child(objCourse.getCourseId())
//                                                  .child(auth.getUid()).setValue(new CreateCourse(c.getCourseName(),sumGride+"",c.getProjectGrade(),c.getAttendanceGrade(),c.getCourseId(),c.getDoctorId(),c.getGradeAvailable()))
//                                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                      @Override
//                                                      public void onSuccess(Void unused) {
//                                                          binding.progeressloadingregister.setVisibility(View.GONE);
//
//                                                          goToFinish(objCourse);
//                                                      }
//                                                  }).addOnFailureListener(new OnFailureListener() {
//                                                      @Override
//                                                      public void onFailure(@NonNull Exception e) {
//                                                          Toast.makeText(Quiz.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                                      }
//                                                  });
//
//
//                                  }
//
//                                  @Override
//                                  public void onCancelled(@NonNull DatabaseError error) {
//
//                                  }
//                              });
//                  }
//              }).addOnFailureListener(new OnFailureListener() {
//                  @Override
//                  public void onFailure(@NonNull Exception e) {
//                      Toast.makeText(Quiz.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                  }
//              });

    }

    private void goToFinish(CreateCourse objCourse){
    Intent intent=new Intent(Quiz.this, AnimationFinishQuiz.class);
    intent.putExtra("ObjectCourse",objCourse);
    startActivity(intent);
    finish();
}

    private void resetAnswer ()
    {
        rightAnswer = 0 ;
        binding.chooseOneInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));
        binding.chooseFoureInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

        binding.chooseThreeInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));

        binding.chooseTwoInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.white));
    }
    private  void init(){
        binding.questionContentInSolveQuze.setText(list.get(position).getQuestion());
        binding.chooseOneInSolveQuze.setText(list.get(position).getAnswer1());
        binding.chooseTwoInSolveQuze.setText(list.get(position).getAnswer2());
        binding.chooseThreeInSolveQuze.setText(list.get(position).getAnswer3());
        binding.chooseFoureInSolveQuze.setText(list.get(position).getAnswer4());
    }
    private  void init2(){
        init();
    if(list.get(position).getRightAnswer()==1){
    binding.chooseOneInSolveQuze
            .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.register_bk_color2));
    }else if(list.get(position).getRightAnswer()==2){
    binding.chooseTwoInSolveQuze
            .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.register_bk_color2));
} else if(list.get(position).getRightAnswer()==3){
    binding.chooseThreeInSolveQuze
            .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.register_bk_color2));
}else if(list.get(position).getRightAnswer()==4){
    binding.chooseFoureInSolveQuze
            .setBackgroundColor(ContextCompat.getColor(Quiz.this, R.color.register_bk_color2));
}
    }

}