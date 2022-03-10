package com.stock24_dz.Fragment.FragmentProfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.stock24_dz.ApiInterface;
import com.stock24_dz.Model.user_model;
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfil extends Fragment {
    TextView name,email,to_profile,to_announce,to_setting,to_about,to_terms,to_privacy_policy,to_feedback,logout;
    AppCompatButton profile;
    ImageView profileImage;
    FirebaseAuth firebaseAuth;
    String firebase_id,s_image;
    ApiInterface service;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        to_profile=view.findViewById(R.id.profil);
        to_announce=view.findViewById(R.id.annonce);
        to_setting=view.findViewById(R.id.setting);
        to_about=view.findViewById(R.id.about);
        to_terms=view.findViewById(R.id.terme);
        to_privacy_policy=view.findViewById(R.id.politique);
        to_feedback=view.findViewById(R.id.feedback);
        logout=view.findViewById(R.id.logout);
        profile=view.findViewById(R.id.to_profile);
        profileImage=view.findViewById(R.id.profileImage);
        firebaseAuth=FirebaseAuth.getInstance();
        firebase_id=firebaseAuth.getCurrentUser().getUid();

        to_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),ActivityAbout.class);
                startActivity(intent);
            }
        });

        to_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),Feedback.class);
                startActivity(intent);
            }
        });



        to_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), Activity_Terms.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
            }
        });

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
                Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void applyChange (user_model user){
        name.setText(user.getFirst_name());
        email.setText(user.getEmail());
       // Glide.with(requireActivity()).load(s_image).into(profileImage);
    }
}
