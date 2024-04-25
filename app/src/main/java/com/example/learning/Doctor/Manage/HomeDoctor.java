package com.example.learning.Doctor.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learning.Adapters.CreateCourseAdapter;
import com.example.learning.Constant;
import com.example.learning.R;

import com.example.learning.Splash.WelcomeScreen;
import com.example.learning.databinding.ActivityHomeDoctorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeDoctor extends AppCompatActivity implements View.OnClickListener {
ActivityHomeDoctorBinding binding;
CreateCourseAdapter adapter=new CreateCourseAdapter();
DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
List<com.example.learning.Moduls.CreateCourse> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeDoctorBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(HomeDoctor.this, R.color.purple_500));
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        binding.btnAddCourse.setOnClickListener(this::onClick);
   ref.child(Constant.COURSE).addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
          if(snapshot.hasChildren()){
              for(DataSnapshot snapshot1:snapshot.getChildren()){
               list.add(snapshot1.getValue(com.example.learning.Moduls.CreateCourse.class));
              }
          }
           adapter.setList(list);
           binding.recyclerInstructorCourse.setAdapter(adapter);
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   });
   binding.btnLogout.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           startActivity(new Intent(HomeDoctor.this, WelcomeScreen.class));
           finish();
       }
   });
adapter.setOnClickItem(new CreateCourseAdapter.OnClickItem() {
    @Override
    public void Onclick(com.example.learning.Moduls.CreateCourse createCourse) {
        Intent intent=new Intent(HomeDoctor.this,PageDoctor.class);
        intent.putExtra("idCourse",createCourse.getCourseId());

        startActivity(intent);

    }
});

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.btnAddCourse.getId()){
            startActivity(new Intent(HomeDoctor.this,CreateCourse.class));

        }
    }
}