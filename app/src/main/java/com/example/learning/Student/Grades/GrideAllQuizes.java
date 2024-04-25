package com.example.learning.Student.Grades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Adapters.AdapterAllRecycle;
import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.databinding.ActivityGride2Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GrideAllQuizes extends AppCompatActivity {
    ActivityGride2Binding binding;
    AdapterAllRecycle adapterAllRecycle = new AdapterAllRecycle();
    ArrayList<String> list = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGride2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(GrideAllQuizes.this, R.color.purple_500));
//        getSupportActionBar().hide();

        CreateCourse ObjCourse = (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
        binding.animat2.setVisibility(View.GONE);
     ref.child(Constant.QUIZ_ANSWER).child(ObjCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
         boolean flage=false;
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             list.clear();

             for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                 for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                     if(snapshot2.getKey().equals(auth.getUid())){
                         list.add(snapshot1.getKey());
                         flage=true;
                     }
                 }

             }
          if(!flage){
              binding.animat2.setVisibility(View.VISIBLE);
              binding.animat2.animate();
              Toast.makeText(GrideAllQuizes.this, "solve the quiz first", Toast.LENGTH_SHORT).show();
          }
             adapterAllRecycle.setType(0);
             adapterAllRecycle.setList(list);
             binding.recyclerGrads.setAdapter(adapterAllRecycle);


         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


        adapterAllRecycle.setOnclickItem(new AdapterAllRecycle.OnclickItem() {
            @Override
            public void Onclick(String s) {
                goToGride(ObjCourse,s);
            }
        });





    }

    private void goToGride(CreateCourse ObjCourse, String s) {
        Intent intent = new Intent(GrideAllQuizes.this, GradeQuizOnly.class);
        intent.putExtra("ObjectCourse", ObjCourse);
        intent.putExtra("position", s);
        startActivity(intent);
    }
}