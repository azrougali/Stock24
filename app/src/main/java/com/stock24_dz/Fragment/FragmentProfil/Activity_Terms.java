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
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Terms extends AppCompatActivity {
    ImageView back;
    TextView privacy;
    ApiInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        back=findViewById(R.id.back);
        privacy=findViewById(R.id.privacy);
        service = RetrofitService.getRetrofitService().create(ApiInterface.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPrivacy_html();
    }

    private void getPrivacy_html() {
        Call<CompanyModel> call=service.getTermes();
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (response.isSuccessful()) {
                    privacy.setText(Html.fromHtml(response.body().getTerms_of_services_fr()));
                }
            }
            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}