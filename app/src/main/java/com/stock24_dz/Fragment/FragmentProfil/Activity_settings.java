package com.stock24_dz.Fragment.FragmentProfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.stock24_dz.ApiInterface;
import com.stock24_dz.Model.user_model;
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_settings extends AppCompatActivity {
    ApiInterface service;
    AppCompatButton profile;
    ImageView profileImage;
    FirebaseAuth firebaseAuth;
    String firebase_id,s_image;
    TextView name,email;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        profile=findViewById(R.id.to_profile);
        profileImage=findViewById(R.id.profileImage);
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //     firebaseAuth=FirebaseAuth.getInstance();
     //   firebase_id=firebaseAuth.getCurrentUser().getUid();

        service = RetrofitService.getRetrofitService().create(ApiInterface.class);
        Call<user_model> call=service.getUserById("BmpoKMfYEaWo1IhgxDznI5hbpxi1");
        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {
                if (response.isSuccessful()) {
                    applyChange(response.body());
                }
            }
            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void applyChange(user_model body) {
        name.setText(body.getFirst_name());
        email.setText(body.getEmail());
        Glide.with(getApplicationContext()).load(s_image).into(profileImage);
    }
}