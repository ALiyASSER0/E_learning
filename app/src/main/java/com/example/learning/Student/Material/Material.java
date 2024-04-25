package com.example.learning.Student.Material;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.learning.Adapters.AdapterAllRecycle;
import com.example.learning.Constant;
import com.example.learning.databinding.ActivityMaterialStudentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Material extends AppCompatActivity {
ActivityMaterialStudentBinding binding;
ArrayList<String> list=new ArrayList<>();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
AdapterAllRecycle adapterAllRecycle=new AdapterAllRecycle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMaterialStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String idCourse=getIntent().getStringExtra("idCourse");
        binding.anm.setVisibility(View.GONE);


        ref.child(Constant.MATERIAL).child(idCourse).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChildren()){
                    binding.anm.animate();
                }else{
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        list.add(snapshot1.getKey());
                    }
                    adapterAllRecycle.setType(2);
                    adapterAllRecycle.setList(list);
                    binding.recyclerMaterial.setAdapter(adapterAllRecycle);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapterAllRecycle.setOnclickItem(new AdapterAllRecycle.OnclickItem() {
            @Override
            public void Onclick(String s) {
                ref.child(Constant.MATERIAL).child(idCourse).child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(snapshot.getValue()+""));
                        startActivity(browserIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }





}