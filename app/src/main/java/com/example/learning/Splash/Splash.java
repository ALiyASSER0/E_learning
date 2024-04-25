package com.example.learning.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.learning.Authentation.Login;
import com.example.learning.R;
import com.example.learning.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {
ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.purple_500));
        getSupportActionBar().hide();
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.animationView.animate();
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
         startActivity(new Intent(Splash.this,WelcomeScreen.class));
             finish();
    }
},5000);
    }
}