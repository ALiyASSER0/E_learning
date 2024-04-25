package com.example.learning.Doctor.Attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Adapters.AdapterAllRecycle;
import com.example.learning.Adapters.AdapterAttendenceOnly;
import com.example.learning.Constant;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;
import com.example.learning.databinding.ActivityShowAllAttendenceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAttendence extends AppCompatActivity {
private ActivityShowAllAttendenceBinding binding;
private AdapterAttendenceOnly adapterAttendenceOnly=new AdapterAttendenceOnly();
private ArrayList<String> list=new ArrayList<>();
private ArrayList<SignOut> list2=new ArrayList<>();
private AdapterAllRecycle adapterAllRecycle=new AdapterAllRecycle();
private DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityShowAllAttendenceBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowAttendence.this, R.color.purple_500));
//        getSupportActionBar().hide();
       setContentView(binding.getRoot());
        String idCourse=getIntent().getStringExtra("idCourse");
        binding.animat.setVisibility(View.GONE);
        ref.child(Constant.ATTENDANCE).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){

                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        list.add(snapshot1.getKey());
                    }

                }else{
                    binding.animat.setVisibility(View.VISIBLE);
                    binding.animat.animate();
                    Toast.makeText(ShowAttendence.this, "no lectures", Toast.LENGTH_SHORT).show();
                }
                adapterAllRecycle.setType(0);
                adapterAllRecycle.setList(list);
            binding.Rec.setAdapter(adapterAllRecycle);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapterAllRecycle.setOnclickItem(new AdapterAllRecycle.OnclickItem() {
            @Override
            public void Onclick(String s) {
                ref.child(Constant.ATTENDANCE).child(idCourse).child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list2.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.hasChildren()){
                                for(DataSnapshot snapshot2:snapshot1.getChildren()){
                                    ref.child(Constant.USERS).child(snapshot2.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            list2.add(snapshot.getValue(SignOut.class));

                                            adapterAttendenceOnly.setLists(list2);
                                            binding.Rec.setAdapter(adapterAttendenceOnly);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(ShowAttendence.this, "No Students in lecture", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
}