package com.stock24_dz.Fragment.FragmentProfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.stock24_dz.ApiInterface;
import com.stock24_dz.Model.user_model;
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditProfile extends AppCompatActivity {
    TextInputEditText name,phone,about;
    ImageView profile_image,couverture_image,save;
    AppCompatButton edit_couverture_image;
    AutoCompleteTextView localisation;
    TextView edit_profile_image;
    ApiInterface service;
    LinearLayout edit_image_profile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        localisation=findViewById(R.id.localisation);
        about=findViewById(R.id.about);
        profile_image=findViewById(R.id.image1);
        couverture_image=findViewById(R.id.image2);
        edit_profile_image=findViewById(R.id.edit_image);
        save=findViewById(R.id.save);
        edit_image_profile=findViewById(R.id.edit_image_profile);
        edit_couverture_image=findViewById(R.id.edit_couverture_image);

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

        localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arr = { "Alger","Boumerdes", "Mostaganem","Jijel"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.select_dialog_item, arr);
                localisation.setAdapter(adapter);

            }
        });
    }

    private void applyChange(user_model body) {
        name.setText(body.getFirst_name());
        phone.setText(body.getPrimary_phone_number());
        localisation.setText(body.getTown_id());
        about.setText(body.getAbout());
        if (!(body.getProfile_image() ==null)){
            Glide.with(getApplicationContext()).load(body.getProfile_image()).into(profile_image);
        }
        if (!(body.getCover_image() ==null)){
            Glide.with(getApplicationContext()).load(body.getCover_image()).into(couverture_image);
        }

    }


}