package com.stock24_dz.Fragment.FragmentProfil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stock24_dz.ApiInterface;
import com.stock24_dz.Model.CompanyModel;
import com.stock24_dz.Model.user_model;
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAbout extends AppCompatActivity {
    
    ImageView back;
    TextView about;
    ApiInterface service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        back=findViewById(R.id.back);
        about=findViewById(R.id.about);
        service = RetrofitService.getRetrofitService().create(ApiInterface.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getabout_html();
    }

    private void getabout_html() {

        Call<CompanyModel> call=service.getAboutUs();
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (response.isSuccessful()) {
                    about.setText(Html.fromHtml(response.body().getAbout_us_fr()));
                }
            }
            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}