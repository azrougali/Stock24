package com.stock24_dz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.stock24_dz.Model.user_model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verfication_phone_number extends AppCompatActivity {
    PinView pinView;
    AppCompatButton register;
    String phone,name,email,company;
    String verificationbysystem;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseAuth firebaseAuth;
    ApiInterface service;
    private View baseView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_phone_number);

        pinView=findViewById(R.id.firstPinView);
        register=findViewById(R.id.register);
        baseView=findViewById(R.id.linearlayout);


        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        company=getIntent().getStringExtra("company");
        phone=getIntent().getStringExtra("phone");

        firebaseAuth =FirebaseAuth.getInstance();
        service = RetrofitService.getRetrofitService().create(ApiInterface.class);



        sendverfication(phone);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin =pinView.getText().toString();
                if (!(pin.isEmpty())){
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationbysystem,pin);
                    VerifyAuth(credential);
                }
                else {
                    register.setText("Entrer votre code");
                }
            }
        });
    }
    private void VerifyAuth(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    adduser();
                }else {
                    register.setText("Le code est erroné");
                }
            }
        });
    }

    private void sendverfication(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+213"+phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationbysystem = s;
            token = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            signinuserbycredential (phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verfication_phone_number.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };
    private void signinuserbycredential(PhoneAuthCredential phoneAuthCredential) {
        final FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(verfication_phone_number.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String firebase_id=firebaseAuth.getCurrentUser().getUid();
                    Call<user_model> call=service.getUserById(firebase_id);
                    call.enqueue(new Callback<user_model>() {
                        @Override
                        public void onResponse(Call<user_model> call, Response<user_model> response) {
                            if (response.isSuccessful()) {
                                if (response.body()==null){
                                    adduser();
                                }else {
                                    Snackbar snackbar1 = Snackbar.make(baseView,R.string.account_existe, Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<user_model> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                        }
                    });

                }else {
                    Toast.makeText(verfication_phone_number.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void adduser() {
        com.stock24_dz.Model.user_model user_model =new user_model();
        user_model.setEmail(email);
        user_model.setFirst_name(name);
        user_model.setPrimary_phone_number(phone);
        user_model.setBadge("silver");
        user_model.setUser_type("user");
        user_model.setApproved(0);
        user_model.setAuto_approve_future_announcements(0);
        user_model.setSubscribed(0);
        user_model.setFirebase_id(firebaseAuth.getCurrentUser().getUid());
        if (!company.isEmpty()){
            user_model.setCompany(company);
        }
        Call<user_model> call=service.CreateUser(user_model);
        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {
                Intent intent=new Intent(getApplicationContext(),category.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}