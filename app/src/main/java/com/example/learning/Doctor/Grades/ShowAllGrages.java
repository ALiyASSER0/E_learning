package com.example.learning.Doctor.Grades;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learning.Adapters.AdapterAllRecycle;
import com.example.learning.Constant;
import com.example.learning.R;
import com.example.learning.databinding.ActivityShowAllQuizesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAllGrages extends AppCompatActivity {
    ActivityShowAllQuizesBinding binding;
    AdapterAllRecycle adapterAllRecycle = new AdapterAllRecycle();
    ArrayList<String> list = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllQuizesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowAllGrages.this, R.color.purple_500));
//        getSupportActionBar().hide();
        binding.animat1.setVisibility(View.GONE);
        String idCourse=getIntent().getStringExtra("idCourse");




             ref.child(Constant.QUIZ).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChildren()){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            list.add(snapshot1.getKey());
                        }
                        adapterAllRecycle.setType(1);
                        adapterAllRecycle.setList(list);
                        binding.recyclerGrads.setAdapter(adapterAllRecycle);
                    }else{
                        binding.animat1.setVisibility(View.VISIBLE);
                    binding.animat1.animate();
                    }


                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });




        adapterAllRecycle.setOnclickItem(new AdapterAllRecycle.OnclickItem() {
            @Override
            public void Onclick(String s) {
                goToGride(s,idCourse,"0");
            }
        });



binding.cardd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        goToGride("",idCourse,"1");
    }
});




    }

    private void goToGride(String s,String idCourse,String type) {
        Intent intent = new Intent(ShowAllGrages.this, ShowGradeOnly.class);
        intent.putExtra("position", s);
        intent.putExtra("idCourse", idCourse);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
