package com.example.library_system.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library_system.EmployeeDashboard;
import com.example.library_system.FeatureController;
import com.example.library_system.StudentDashboard;
import com.example.library_system.databinding.LoginFragmentBinding;
import com.example.library_system.modal_class.UserModal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment {
    LoginFragmentBinding binding;
    FirebaseDatabase database;
    SharedPreferences preferences;
    SharedPreferences.Editor preferenceseditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        preferences = getActivity().getSharedPreferences("User_details", Context.MODE_PRIVATE);
        preferenceseditor =preferences.edit();
        database = FirebaseDatabase.getInstance();
        int user_type = FeatureController.getController().getUser_type();
        binding.loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loading.setVisibility(View.VISIBLE);
                binding.loginbut.setVisibility(View.GONE);

                if (binding.emailinput != null && !binding.emailinput.getText().toString().equals("")) {
                    String email = binding.emailinput.getText().toString().toLowerCase(Locale.ROOT);
                    if (emailisvalid(email)) {
                        if (binding.passinput != null && !binding.passinput.getText().toString().equals("")) {
                            int index = email.indexOf("@");
                            String strtemail = email.substring(0, index);
                            if (user_type == 0) {
                                database.getReference().child("Student_info").child(strtemail).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            UserModal user = snapshot.getValue(UserModal.class);
                                            if (email.equals(user.getUser_email())) {
                                                if (binding.passinput.getText().toString().equals(user.getUser_pass())) {
                                                    binding.loading.setVisibility(View.GONE);
                                                    FeatureController.getController().setEmp_email(user.getUser_email());
                                                   if(binding.saveuser.isChecked())
                                                   {
                                                       preferenceseditor.putString("username", user.getUser_email());
                                                       preferenceseditor.putString("userpass", user.getUser_pass());
                                                       // false for student
                                                       preferenceseditor.putBoolean("usertype", false);
                                                       preferenceseditor.commit();
                                                   }
                                                    Toast.makeText(getActivity(), " " + user.getUser_email(), Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getActivity(), StudentDashboard.class));
                                                } else {
                                                    binding.loading.setVisibility(View.GONE);
                                                    binding.loginbut.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getActivity(), "Password Incorrect", Toast.LENGTH_SHORT).show();
                                                }
                                                binding.loading.setVisibility(View.GONE);
                                                binding.loginbut.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), "User present", Toast.LENGTH_SHORT).show();
                                            } else {
                                                binding.loading.setVisibility(View.GONE);
                                                binding.loginbut.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), "User not present", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            } else {


                                database.getReference().child("Employee_info").child(strtemail).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            UserModal user = snapshot.getValue(UserModal.class);
                                            if (email.equals(user.user_email)) {
                                                if (binding.passinput.getText().toString().equals(user.user_pass)) {
                                                    binding.loading.setVisibility(View.GONE);
                                                    FeatureController.getController().setEmp_email(user.getUser_email());
                                                   if(binding.saveuser.isChecked())
                                                   {
                                                       preferenceseditor.putString("username", user.getUser_email());
                                                       preferenceseditor.putString("userpass", user.getUser_pass());
                                                       // true for employee
                                                       preferenceseditor.putBoolean("usertype", true);
                                                       preferenceseditor.commit();
                                                   }
                                                    Toast.makeText(getActivity(), "Password correct", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getActivity(), EmployeeDashboard.class);
                                                    FeatureController.getController().setEmp_name(user.getUser_name());
                                                    intent.putExtra("username", user.getUser_name());
                                                    startActivity(intent);
                                                } else {
                                                    binding.loading.setVisibility(View.GONE);
                                                    binding.loginbut.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getActivity(), "Password Incorrect", Toast.LENGTH_SHORT).show();
                                                }
                                                binding.loading.setVisibility(View.GONE);
                                                binding.loginbut.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), "User present", Toast.LENGTH_SHORT).show();
                                            } else {
                                                binding.loading.setVisibility(View.GONE);
                                                binding.loginbut.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), "User not present", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        } else {
                            binding.loading.setVisibility(View.GONE);
                            binding.loginbut.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                } else {
                    binding.loading.setVisibility(View.GONE);
                    binding.loginbut.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Please enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    public static boolean emailisvalid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
