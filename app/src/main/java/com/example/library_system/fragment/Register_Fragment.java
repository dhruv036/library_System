package com.example.library_system.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.library_system.FeatureController;
import com.example.library_system.databinding.RegisterfragmentBinding;
import com.example.library_system.modal_class.UserModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.regex.Pattern;

public class Register_Fragment extends Fragment {
    RegisterfragmentBinding fragmentBinding;
    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = RegisterfragmentBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        int user_type = FeatureController.getController().getUser_type();
        fragmentBinding.subbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentBinding.loading.setVisibility(View.VISIBLE);
                fragmentBinding.subbut.setVisibility(View.GONE);
                if (!(fragmentBinding.emailinput.getText().toString().equals("")) && fragmentBinding.emailinput.getText() != null) {
                    String email = fragmentBinding.emailinput.getText().toString().toLowerCase(Locale.ROOT);

                    if (emailisvalid(email)) {
                        int index = email.indexOf("@");
                        String stremail = email.substring(0, index);
                        String user = "Student_info";
                        if(user_type == 0)
                        {
                            user = "Student_info";
                        }else {
                            user = "Employee_info";
                        }
                        database.getReference().child(user).child(stremail).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    UserModal user = snapshot.getValue(UserModal.class);

                                    if (email.equals(user.user_email)) {
                                        fragmentBinding.loading.setVisibility(View.GONE);
                                        fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), "User present Already please Login", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                } else {
                                    String pass = fragmentBinding.passinput.getText().toString();
                                    String name = fragmentBinding.nameinput.getText().toString();
                                    if (!(name.equals("")) && name != null) {
                                        if (!(fragmentBinding.conpassinput.getText().toString().equals("") || fragmentBinding.passinput.getText().toString().equals(""))) {
                                            if (fragmentBinding.conpassinput.getText().toString().equals(pass)) {
                                                UserModal userr = new UserModal(name, email, pass);
                                                if (user_type == 0) {
                                                    database.getReference().child("Student_info").child(stremail).setValue(userr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            fragmentBinding.loading.setVisibility(View.GONE);
                                                            fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                                            int index  =email.indexOf("@");
                                                            String stuem = email.substring(0,index);
                                                            database.getReference().child("Issued_Book_Stu").child(stuem).child("noofbook").setValue(0);
                                                            Toast.makeText(getActivity(), "Student Registered", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            fragmentBinding.loading.setVisibility(View.GONE);
                                                            fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                                            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    database.getReference().child("Employee_info").child(stremail).setValue(userr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            fragmentBinding.loading.setVisibility(View.GONE);
                                                            fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                                            Toast.makeText(getActivity(), "Teacher Registered", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            fragmentBinding.loading.setVisibility(View.GONE);
                                                            fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                                            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                            } else {

                                                fragmentBinding.loading.setVisibility(View.GONE);
                                                fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                                Toast.makeText(getActivity(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {

                                            fragmentBinding.loading.setVisibility(View.GONE);
                                            fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                            Toast.makeText(getActivity(), "Please enter Password ", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        fragmentBinding.loading.setVisibility(View.GONE);
                                        fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), "Please Enter your Name", Toast.LENGTH_SHORT).show();
                                    }
                                    fragmentBinding.loading.setVisibility(View.GONE);
                                    fragmentBinding.subbut.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "User not present", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        fragmentBinding.loading.setVisibility(View.GONE);
                        fragmentBinding.subbut.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Please enter Correct Email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    fragmentBinding.loading.setVisibility(View.GONE);
                    fragmentBinding.subbut.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Please enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragmentBinding.getRoot();
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