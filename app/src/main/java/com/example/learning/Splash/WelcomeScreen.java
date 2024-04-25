package com.example.learning.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.learning.Authentation.Login;
import com.example.learning.R;
import com.example.learning.databinding.ActivityWelcomeScreenBinding;

public class WelcomeScreen extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
ActivityWelcomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(WelcomeScreen.this, R.color.purple_500));
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      binding.circleImageView2.animate();
        binding.studentCheckBox.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.doctorCheckBox.setOnCheckedChangeListener(this::onCheckedChanged);
        binding.btnNext.setOnClickListener(this::onClick);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId()==binding.studentCheckBox.getId()){
            if(isChecked){
                binding.doctorCheckBox.setChecked(false);
                binding.studentCheckBox.setChecked(isChecked);
            }
        }else if(buttonView.getId()==binding.doctorCheckBox.getId()){
            if(isChecked){
                binding.studentCheckBox.setChecked(false);
                binding.doctorCheckBox.setChecked(isChecked);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.btnNext.getId()){
           if(binding.doctorCheckBox.isChecked()){
               Intent intent=new Intent(WelcomeScreen.this, Login.class);
               intent.putExtra("check","doctor");
           startActivity(intent);
           finish();
           } else if(binding.studentCheckBox.isChecked()){
               Intent intent=new Intent(WelcomeScreen.this, Login.class);
               intent.putExtra("check","student");
               startActivity(intent);
               finish();
           }else{
               Toast.makeText(this, "please Check", Toast.LENGTH_SHORT).show();
           }
        }
    }
}