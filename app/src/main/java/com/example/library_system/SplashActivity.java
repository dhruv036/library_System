package com.example.library_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.library_system.databinding.SplashLayoutBinding;
import com.example.library_system.modal_class.UserModal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    SplashLayoutBinding binding;

    FirebaseDatabase database;
    String username, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();


        SharedPreferences preferences = getSharedPreferences("User_details", MODE_PRIVATE);

        String tempname;
        boolean usertype;
        if (preferences.getString("username", null) != null) {
            //User saved
            username = preferences.getString("username", null);
            int index = username.indexOf("@");
            tempname = username.substring(0, index);
            pass = preferences.getString("userpass", null);
            usertype = preferences.getBoolean("usertype", false);
            String type = "";
            Intent intent;
            if (usertype) {    // employee
                type = "Employee_info";
                FeatureController.getController().setUser_type(1);
                 intent = new Intent(SplashActivity.this, EmployeeDashboard.class);
            } else {   // student
                type = "Student_info";
                FeatureController.getController().setUser_type(0);
                intent = new Intent(SplashActivity.this, StudentDashboard.class);
            }
            database.getReference().child(type).child(tempname).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        UserModal user = snapshot.getValue(UserModal.class);
                        if (username.equals(user.getUser_email())) {
                            if (pass.equals(user.getUser_pass())) {
                                FeatureController.getController().setEmp_email(user.getUser_email());
                                FeatureController.getController().setEmp_name(user.getUser_name());
                                intent.putExtra("username", user.getUser_name());
                                startActivity(intent);
                            }else {
                                Toast.makeText(SplashActivity.this, "Plz Login Again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


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

    }
}