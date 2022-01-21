package com.example.library_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.library_system.databinding.ActivityStudentDashboardBinding;

public class StudentDashboard extends AppCompatActivity {
ActivityStudentDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityStudentDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}