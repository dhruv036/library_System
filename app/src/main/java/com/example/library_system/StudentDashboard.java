package com.example.library_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.library_system.adapter.StuIssuedAdapter;
import com.example.library_system.databinding.ActivityStudentDashboardBinding;
import com.example.library_system.modal_class.Bookmodal_Stu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDashboard extends AppCompatActivity {
ActivityStudentDashboardBinding binding;
FirebaseDatabase database;
    SharedPreferences preferences;
    SharedPreferences.Editor preferenceseditor;
String stu_email;
ArrayList<Bookmodal_Stu> list =new ArrayList<>();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            preferenceseditor.clear().commit();
            Toast.makeText(StudentDashboard.this, "Teacher Logout", Toast.LENGTH_SHORT).show();
            finishAffinity();

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityStudentDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database =FirebaseDatabase.getInstance();
        preferences = getSharedPreferences("User_details", Context.MODE_PRIVATE);
        preferenceseditor =preferences.edit();
        stu_email = FeatureController.getController().getEmp_email();
        binding.textview.setText(getIntent().getStringExtra("username"));
        int index  =stu_email.indexOf("@");
        String stuem = stu_email.substring(0,index);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        database.getReference().child("Issued_Book_Stu").child(stuem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int ii = snapshot.child("noofbook").getValue(Integer.class);
                    binding.noOfBook.setText(String.valueOf(ii));
                    for (DataSnapshot snapshot1 : snapshot.child("Books").getChildren())
                    {
                        Bookmodal_Stu stu = snapshot1.getValue(Bookmodal_Stu.class);
                        list.add(stu);
                    }
                    binding.recycleview.setAdapter(new StuIssuedAdapter(StudentDashboard.this,list));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}