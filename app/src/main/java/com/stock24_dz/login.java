package com.stock24_dz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.stock24_dz.Model.user_model;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login extends AppCompatActivity {
    AppCompatButton facebook_login,phone_login,login;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;
    FirebaseAuth mAuth;
    TextView to_register;
    TextInputEditText phone;
    PinView pinView;
    String verificationbysystem;
    PhoneAuthProvider.ForceResendingToken token;
    private View baseView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        baseView=findViewById(R.id.relativelayout);
        login=findViewById(R.id.login);
        phone=(TextInputEditText)findViewById(R.id.phone);
        phone_login=findViewById(R.id.send_code);
        pinView=findViewById(R.id.firstPinView);
        to_register =findViewById(R.id.to_register);
        to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
            }
        });



        phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber=phone.getText().toString();
                if (phonenumber.length()<10){
                    Toast.makeText(getApplicationContext(),R.string.toast_phone_number,Toast.LENGTH_LONG).show();
                }else {
                    sendverfication(phone.getText().toString());
                }
               phone_login.setVisibility(View.GONE);
               login.setVisibility(View.VISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_pin=pinView.getText().toString();
                if (!s_pin.isEmpty()){
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationbysystem,s_pin);
                    VerifyAuth(credential);
                }else {
                    login.setText("Entrer votre code");
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("794921544539-bqqr7kpvifbcaolqm19616imsvfllgr3.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account =GoogleSignIn.getLastSignedInAccount(this);
        SignInButton signInButton=findViewById(R.id.gmail_login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        facebook_login=findViewById(R.id.facebook_login);
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(login.this,login_facebook.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void sendverfication(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+213"+number,        // Phone number to verify
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
            Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void signinuserbycredential(PhoneAuthCredential phoneAuthCredential) {
        final FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Snackbar snackbar1 = Snackbar.make(baseView, R.string.create_account, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                }
            }
        });
    }

    private void VerifyAuth(PhoneAuthCredential credential) {
        final FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Snackbar snackbar1 = Snackbar.make(baseView, R.string.create_account, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                } else {
                    login.setText("Le code est erroné");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth =FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        com.stock24_dz.Model.user_model user_model=new user_model();
        user_model.setFirst_name(user.getDisplayName());
        user_model.setEmail(user.getEmail());
        user_model.setFirebase_id(user.getUid());
        user_model.setProfile_image(String.valueOf(user.getPhotoUrl()));
        user_model.setBadge("silver");
        user_model.setUser_type("user");
        user_model.setApproved(0);
        user_model.setAuto_approve_future_announcements(0);
        user_model.setSubscribed(0);

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(Const.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<user_model> call=apiInterface.CreateUser(user_model);
        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {
                Toast.makeText(getApplicationContext(),user_model.getFirst_name()+" added",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }




}