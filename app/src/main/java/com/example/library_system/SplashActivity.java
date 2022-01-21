package com.example.library_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.library_system.databinding.SplashLayoutBinding;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
 SplashLayoutBinding binding;
 FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();


        binding.logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.stubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeatureController.getController().setUser_type(0);
                binding.logB.setVisibility(View.VISIBLE);
                binding.card.setVisibility(View.GONE);
            }
        });
        binding.teacherbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeatureController.getController().setUser_type(1);
                binding.logB.setVisibility(View.VISIBLE);
            binding.card.setVisibility(View.GONE);
            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },2000);
    }
}