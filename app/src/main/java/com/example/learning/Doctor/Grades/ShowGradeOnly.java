package com.example.learning.Doctor.Grades;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Toast;


import com.example.learning.Adapters.AdapterQuizOnly;
import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.Moduls.ModelAnswerQuiz;
import com.example.learning.Moduls.ShowAllGrades;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;
import com.example.learning.databinding.ActivityQuizOnlyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowGradeOnly extends AppCompatActivity {
    ActivityQuizOnlyBinding binding;
    private ArrayList<ModelAnswerQuiz> list = new ArrayList<>();
    private ArrayList<SignOut> list2 = new ArrayList<>();
    private ArrayList<ShowAllGrades> list3 = new ArrayList<>();
    private AdapterQuizOnly adapterQuizOnly = new AdapterQuizOnly();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizOnlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowGradeOnly.this, R.color.purple_500));
//        getSupportActionBar().hide();

        String position = getIntent().getStringExtra("position");
        String idCourse = getIntent().getStringExtra("idCourse");
        String type = getIntent().getStringExtra("type");
        if (type.equals("0")) {
      ShowOnlyGradeOfOnlyQuiz(idCourse,position);
        } else {
            showAllGradesOfAllQuizes(idCourse);
        }


    }

    private void ShowOnlyGradeOfOnlyQuiz(String idCourse,String position){

    ref.child(Constant.QUIZ_ANSWER).child(idCourse).child(position).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            list2.clear();
            list.clear();

            if(!snapshot.hasChildren()){
                Toast.makeText(ShowGradeOnly.this, "No Student Solve the Quiz", Toast.LENGTH_LONG).show();
            }else{
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ref.child(Constant.USERS).child(snapshot1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list2.add(snapshot.getValue(SignOut.class));
                            list.add(snapshot1.getValue(ModelAnswerQuiz.class));
                            adapterQuizOnly.setLists(list2,list);
                            binding.recyclerGrads.setAdapter(adapterQuizOnly);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }






        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
    private void showAllGradesOfAllQuizes(String idCourse) {
        ref.child(Constant.COURSES_OF_STUDENT).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChildren()){
                    Toast.makeText(ShowGradeOnly.this, "No Quizes solved", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(Constant.COURSE).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                list3.clear();
                CreateCourse c = snapshot.getValue(CreateCourse.class);
                ref.child(Constant.COURSES_OF_STUDENT).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            CreateCourse c2 = snapshot1.getValue(CreateCourse.class);
                            ref.child(Constant.USERS).child(snapshot1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   SignOut z=snapshot.getValue(SignOut.class);
                                    list2.add(snapshot.getValue(SignOut.class));
                                    list3.add(new ShowAllGrades(c.getGradeQuiz(), c2.getGradeQuiz()));
                                    adapterQuizOnly.setList3(list3, list2);
                                    binding.recyclerGrads.setAdapter(adapterQuizOnly);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}



