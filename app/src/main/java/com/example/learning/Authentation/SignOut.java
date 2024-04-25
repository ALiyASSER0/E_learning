package com.example.learning.Authentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;

import com.example.learning.Doctor.Manage.HomeDoctor;
import com.example.learning.R;
import com.example.learning.Student.Manage.HomeStudent;
import com.example.learning.databinding.ActivitySignOutBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignOut extends AppCompatActivity implements View.OnClickListener {
ActivitySignOutBinding binding ;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
String Check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(SignOut.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding=ActivitySignOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
     binding.animationView.animate();
        Check=getIntent().getStringExtra("check");

    binding.registerButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.registerButton.getId()){
            reciveAttribute();
        }
    }

    private void reciveAttribute() {
    String Name=binding.editTextName.getText().toString().trim();
    String Email=binding.editTextEmail.getText().toString().trim();
    String pass=binding.editTextPassword.getText().toString().trim();
            validation(Name,Email,pass);

    }



public void validation(String Name,String Email,String pass){
    binding.progeressloadingregister.setVisibility(View.VISIBLE);
    if(Name.isEmpty()){
        binding.progeressloadingregister.setVisibility(View.GONE);
        binding.editTextName.setError(getString(R.string.req));
    }else if(Email.isEmpty()){
        binding.progeressloadingregister.setVisibility(View.GONE);
        binding.editTextEmail.setError(getString(R.string.req));
    }else if(pass.isEmpty()){
        binding.progeressloadingregister.setVisibility(View.GONE);
        binding.editTextPassword.setError(getString(R.string.req));
    }else{
        sentDataToAuthentation(Email,pass,Name);

    }
}

    private void sentDataToAuthentation(String email, String pass,String Name) {
           auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
               @Override
               public void onSuccess(AuthResult authResult) {
                   sentDataToRealTime(Name,email);
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   binding.progeressloadingregister.setVisibility(View.GONE);
                   Toast.makeText(SignOut.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               }
           });
    }

    public void sentDataToRealTime(String Name,String Email){

   if(Check.equals("student")){
       createRealTime(Constant.STUDENT,Name,Email);
   }else if(Check.equals("doctor")){
       createRealTime(Constant.DOCTOR,Name,Email);
   }


}
public void createRealTime(String type,String Name,String Email){
    String ID=auth.getUid();
        ref.child(Constant.USERS).child(ID).setValue(new com.example.learning.Moduls.SignOut(ID,Name,Email,type))
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    binding.progeressloadingregister.setVisibility(View.GONE);
                    if(Check.equals("student")){
                        startActivity(new Intent(SignOut.this, HomeStudent.class));
                        finish();
                    }else if(Check.equals("doctor")){
                        startActivity(new Intent(SignOut.this, HomeDoctor.class));
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.progeressloadingregister.setVisibility(View.GONE);
                    Toast.makeText(SignOut.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
}

}