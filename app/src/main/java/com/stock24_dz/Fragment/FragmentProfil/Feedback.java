package com.stock24_dz.Fragment.FragmentProfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stock24_dz.ApiInterface;
import com.stock24_dz.Model.CompanyModel;
import com.stock24_dz.R;
import com.stock24_dz.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends AppCompatActivity {
    EditText text;
    AppCompatButton send;
    ApiInterface service;
    String email,feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        text=findViewById(R.id.text);
        send=findViewById(R.id.send);
        service = RetrofitService.getRetrofitService().create(ApiInterface.class);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=text.getText().toString();
                if (feedback.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ecrire le feedback",Toast.LENGTH_LONG).show();
                }else {
                    getemail();
                }

            }
        });
    }



    private void getemail() {
        Call<CompanyModel> call=service.getEmail();
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (response.isSuccessful()) {
                    email=response.body().getEmail();


                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    intent.putExtra(Intent.EXTRA_TEXT,feedback);
                    intent.setData(Uri.parse("mailto:"+email)); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


}