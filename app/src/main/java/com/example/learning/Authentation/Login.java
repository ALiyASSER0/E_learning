package com.example.learning.Authentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Doctor.Manage.HomeDoctor;
import com.example.learning.R;
import com.example.learning.Student.Manage.HomeStudent;
import com.example.learning.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {
ActivityLoginBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
String Check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding=ActivityLoginBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.purple_500));

        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        Check=getIntent().getStringExtra("check");
        binding.signUpBtn.setOnClickListener(this::onClick);
   binding.loginButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.signUpBtn.getId()){
            Intent intent=new Intent(Login.this,SignOut.class);
              intent.putExtra("check",Check);
               startActivity(intent);
               finish();
        }else if(v.getId()==binding.loginButton.getId()){
            reciveAttribute();
        }
    }

    private void reciveAttribute() {
        String Email=binding.editTextEmail.getText().toString().trim();
        String pass=binding.editTextPassword.getText().toString().trim();
        validation(Email,pass);
    }
    public void validation(String Email,String pass){
        binding.progeressloadingregister.setVisibility(View.VISIBLE);
         if(Email.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editTextEmail.setError(getString(R.string.req));
        }else if(pass.isEmpty()){
            binding.progeressloadingregister.setVisibility(View.GONE);
            binding.editTextPassword.setError(getString(R.string.req));
        }else{
             sentDataToAuthentation(Email,pass);
        }
    }

public void sentDataToAuthentation(String email, String pass){
    auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            binding.progeressloadingregister.setVisibility(View.GONE);
            if (Check.equals("student")) {
                startActivity(new Intent(Login.this, HomeStudent.class));
                 finish();
            } else if (Check.equals("doctor")) {
                startActivity(new Intent(Login.this, HomeDoctor.class));
                 finish();
             }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            binding.progeressloadingregister.setVisibility(View.GONE);
            Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

}