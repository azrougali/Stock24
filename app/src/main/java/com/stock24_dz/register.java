package com.stock24_dz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.stock24_dz.Adapter.adapter_category;
import com.stock24_dz.Model.category_model;
import com.stock24_dz.Model.user_model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;
    AppCompatButton register,verify_code;
    ImageView arrow_left;
    TextInputEditText email,name,company,phone;
    CheckBox checkBox;
    String s_name,s_email,s_company,s_phone;
    private View baseView;
    PinView pinView;
    Boolean auth=false;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    ApiInterface service;
    private CallbackManager callbackManager;
    String acces_token,profile_picture,f_name,f_email,facebook_id;
    private LoginButton loginButton;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        arrow_left=findViewById(R.id.arrow_left);
        baseView=findViewById(R.id.linearlayout);
        email=(TextInputEditText)findViewById(R.id.email);
        name=(TextInputEditText)findViewById(R.id.name);
        company=(TextInputEditText)findViewById(R.id.company);
        phone=(TextInputEditText)findViewById(R.id.phone);
        verify_code=findViewById(R.id.send_code);
        checkBox=findViewById(R.id.checkbox);
        register=findViewById(R.id.register);
        pinView=findViewById(R.id.firstPinView);
        loginButton=(LoginButton)findViewById(R.id.facebook_login);

        firebaseAuth =FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();


        service = RetrofitService.getRetrofitService().create(ApiInterface.class);
        callbackManager = CallbackManager.Factory.create();


        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                acces_token= loginResult.getAccessToken().getToken();
                f_name=Profile.getCurrentProfile().getName();
                facebook_id=loginResult.getAccessToken().getUserId();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (object.has("email")) {
                                f_email = object.getString("email");
                                profile_picture=response.getJSONObject().getJSONObject("picture").getJSONObject("data").getString("url");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "erreur login", Toast.LENGTH_SHORT).show();
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name=name.getText().toString();
                s_company=company.getText().toString();
                s_email=email.getText().toString();
                s_phone=phone.getText().toString();

                if (s_phone.length()!=10){
                    Snackbar snackbar1 = Snackbar.make(baseView, R.string.toast_phone, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else if(auth=false){
                    Snackbar snackbar1 = Snackbar.make(baseView, R.string.toast_phone_number, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else if (s_name.length()<3){
                    Snackbar snackbar1 = Snackbar.make(baseView, R.string.toast_email, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else if (!isEmailValid(s_email)){
                    Snackbar snackbar1 = Snackbar.make(baseView, R.string.toast_email, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else if(!checkBox.isChecked()){
                    Snackbar snackbar1 = Snackbar.make(baseView, R.string.toast_checkbox, Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }else {
                    Intent intent=new Intent(getApplicationContext(),verfication_phone_number.class);
                    intent.putExtra("phone",s_phone);
                    intent.putExtra("company",s_company);
                    intent.putExtra("email",s_email);
                    intent.putExtra("name",s_name);
                    startActivity(intent);
                }
            }
        });
    }
    private void handleFacebookAccessToken(AccessToken accessToken) {
        loginButton.setVisibility(View.GONE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String firebase_id=user.getUid();
                            Call<user_model> call=service.getUserById(firebase_id);
                            call.enqueue(new Callback<user_model>() {
                                @Override
                                public void onResponse(Call<user_model> call, Response<user_model> response) {
                                    if (response.isSuccessful()) {
                                       if (response.body()==null){
                                           adduser_facebook(user);
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
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            adduser_facebook(null);
                        }
                    }
                });
    }

    private void adduser_facebook(FirebaseUser user) {
        com.stock24_dz.Model.user_model user_model=new user_model();
        user_model.setFirst_name(f_name);
        user_model.setEmail(f_email);
        user_model.setFirebase_id(user.getUid());
        user_model.setProfile_image(profile_picture);
        user_model.setBadge("silver");
        user_model.setUser_type("user");
        user_model.setApproved(0);
        user_model.setAuto_approve_future_announcements(0);
        user_model.setSubscribed(0);
        user_model.setFacebook_id(facebook_id);
        user_model.setFacebook_access_token(acces_token);
        Call<user_model> call=service.CreateUser(user_model);
        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(String s_email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(s_email).matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

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
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String firebase_id=user.getUid();
                            Call<user_model> call=service.getUserById(firebase_id);
                            call.enqueue(new Callback<user_model>() {
                                @Override
                                public void onResponse(Call<user_model> call, Response<user_model> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body()==null){
                                            adduser_gmail(user);
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

                        }
                        else {

                            // If sign in fails, display a message to the user.
                            adduser_gmail(null);
                        }
                    }
                });
    }

    private void adduser_gmail(FirebaseUser user) {
        com.stock24_dz.Model.user_model user_model =new user_model();
        user_model.setEmail(user.getEmail());
        user_model.setFirst_name(user.getDisplayName());
        user_model.setPrimary_phone_number(user.getPhoneNumber());
        user_model.setBadge("");
        user_model.setApproved(0);
        user_model.setSubscribed(0);
        user_model.setAuto_approve_future_announcements(0);
        user_model.setUser_type("user");
        user_model.setProfile_image(user.getPhotoUrl().toString());
        user_model.setFirebase_id(user.getUid());
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