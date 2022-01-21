package com.example.library_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.library_system.databinding.LoginLayoutBinding;
import com.example.library_system.fragment.Login_Fragment;
import com.example.library_system.fragment.Register_Fragment;
import com.polyak.iconswitch.IconSwitch;

public class LoginActivity extends AppCompatActivity {
    LoginLayoutBinding binding;
    boolean loginType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       Fragment fragment1 = null ;
        fragment1 = new Register_Fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.loadLayout,fragment1).commit();

        binding.loginToggle.setThumbColorLeft(getColor(R.color.black));
        binding.loginToggle.setThumbColorRight(getColor(R.color.black));
        //     binding.loginToggle.setChecked(IconSwitch.Checked.LEFT);
        binding.loginToggle.setInactiveTintIconRight(getColor(R.color.isw_defaultBg));
        binding.loginToggle.setInactiveTintIconLeft(getColor(R.color.isw_defaultBg));

        binding.loginToggle.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                Fragment fragment = null;
                if (current == IconSwitch.Checked.LEFT) {
                    loginType = true;
                    binding.text.setText("Already have an account? ");
                    binding.sinbut.setText("Sign in ->");
                    Toast.makeText(LoginActivity.this, "Left Checked", Toast.LENGTH_SHORT).show();
                    fragment = new Register_Fragment();
                } else {
                    loginType = false;
                    fragment = new Login_Fragment();
                    binding.text.setText("Don't have an account? ");
                    binding.sinbut.setText("Sign up ->");
                    Toast.makeText(LoginActivity.this, "Right Checked", Toast.LENGTH_SHORT).show();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.loadLayout,fragment).commit();



            }

        });




        getSupportActionBar().hide();
    }
}