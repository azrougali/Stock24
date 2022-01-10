package com.stock24_dz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stock24_dz.Fragment.FragmenHome.fragmentHome;

public class home extends AppCompatActivity {

    BottomNavigationView  bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

       // bottomNavigationView.setOnNavigationItemSelectedListener(navlisetener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new fragmentHome()).commit();


    }
}