package com.example.learning.Doctor.Attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.R;
import com.example.learning.databinding.ActivityAttendanceDoctorBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TakeAttendence extends AppCompatActivity  {
ActivityAttendanceDoctorBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
   static int i=1;
    int generateCode;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(TakeAttendence.this, R.color.purple_500));
//        getSupportActionBar().hide();
        binding=ActivityAttendanceDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    String idCourse=getIntent().getStringExtra("idCourse");
binding.btnGenerateCode.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         generateCode=generateCode();
        binding.textCode.setText(generateCode+"");
        binding.btnConfirm.setEnabled(true);
    }
});
binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        binding.progeressloadingregister.setVisibility(View.VISIBLE);

        ref.child(Constant.ATTENDANCE).child(idCourse).child("Lecture: "+(i++)).child(generateCode+"").setValue("")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.progeressloadingregister.setVisibility(View.GONE);
                        Toast.makeText(TakeAttendence.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progeressloadingregister.setVisibility(View.GONE);
                        Toast.makeText(TakeAttendence.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
});


}


    public  int generateCode() {
     //generiate Random value in int from 4 to 10000:
        int min=4;
        int max=10000;
        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);

        return random_int;
    }


}