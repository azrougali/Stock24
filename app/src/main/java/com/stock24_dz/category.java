package com.stock24_dz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.stock24_dz.Adapter.adapter_category;
import com.stock24_dz.Model.category_model;
import com.stock24_dz.Model.user_interest;
import com.stock24_dz.Model.user_model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class category extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<category_model> liste_category;
    AppCompatButton nexthomme,pass;
    FirebaseAuth firebaseAuth;
    private List<category_model> selected_item=new ArrayList<>();
    String firebase_id;
    ApiInterface service;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerview_id);
        nexthomme=(AppCompatButton)findViewById(R.id.tohomme);
        pass=findViewById(R.id.ignore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        liste_category =new ArrayList<category_model>();

        firebaseAuth=FirebaseAuth.getInstance();

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(Const.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<List<category_model>> call=apiInterface.getAllCategories();

        call.enqueue(new Callback<List<category_model>>() {
            @Override
            public void onResponse(Call<List<category_model>> call, Response<List<category_model>> response) {
                liste_category= (ArrayList<category_model>) response.body();
                if (response.isSuccessful()) {
                    if (liste_category != null) {
                        com.stock24_dz.Adapter.adapter_category adapter = new adapter_category(getApplicationContext(), liste_category);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<category_model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),home.class);
                startActivity(intent);
            }
        });

        nexthomme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebase_id=firebaseAuth.getCurrentUser().getUid();
                com.stock24_dz.Adapter.adapter_category adapter = new adapter_category(getApplicationContext(), liste_category);
                selected_item=adapter.get_item_selected();
                if (selected_item.size()>0){
                    Toast.makeText(getApplicationContext(),"bien",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"mauvais",Toast.LENGTH_SHORT).show();

                }
                for (int i=0;i<selected_item.toArray().length;i++){
                    adduser_interest(i,selected_item.get(i).getId());
                }

            }
        });

    }

    private void adduser_interest(int i, String id) {
        service = RetrofitService.getRetrofitService().create(ApiInterface.class);
        com.stock24_dz.Model.user_interest user_interest=new com.stock24_dz.Model.user_interest();
        user_interest.setUser_id(i);
        user_interest.setCategory_id(Integer.parseInt(id));
        Call<com.stock24_dz.Model.user_interest> call=service.addInterest(user_interest);
        call.enqueue(new Callback<user_interest>() {
            @Override
            public void onResponse(Call<user_interest> call, Response<user_interest> response) {
                Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<user_interest> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }


}