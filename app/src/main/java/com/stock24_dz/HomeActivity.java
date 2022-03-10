package com.stock24_dz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stock24_dz.Fragment.AddAnnonce.FragmentAnnonce;
import com.stock24_dz.Fragment.FragmenHome.fragmentHome;
import com.stock24_dz.Fragment.FragmentProfil.FragmentProfil;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView  bottomNavigationView;
    ImageView floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(navlisetener);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlisetener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new fragmentHome()).commit();

        floatingActionButton= findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               com.stock24_dz.Fragment.AddAnnonce.FragmentAnnonce selectedFragment=new FragmentAnnonce();
               getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlisetener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    selectedFragment = new fragmentHome();
                    break;
                case R.id.profil:
                    selectedFragment = new FragmentProfil();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();
            return true;
        }
    };

}