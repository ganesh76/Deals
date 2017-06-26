package com.g.deals.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.g.deals.R;
import com.g.deals.util.NetworkDetails;

import org.json.JSONObject;

import static com.g.deals.activities.SplashScreen.pref;

public class LoginActivity extends AppCompatActivity {
    TextView txtStatus;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        setupWindowAnimations();
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Profile profile = Profile.getCurrentProfile();

                        if(profile!=null)
                        {
                            Log.i("from","profile");
                            editor.putString("loginFlag", "1");
                            editor.putString("userName", profile.getName());
                            editor.putString("userID", profile.getId());
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Welcome "+profile.getName(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Log.i("from","graph");
                           final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                            pd.setCancelable(false);
                          GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response)
                                    {
                                        pd.dismiss();
                                        editor.putString("loginFlag", "1");
                                        editor.putString("userName", object.optString("name","User"));
                                        editor.putString("userID", object.optString("id",""));
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(),"Welcome "+object.optString("name","User"),Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
                                    }
                                });
                            pd.show();
                        request.executeAsync();
                        }
                    }

                    @Override
                    public void onCancel() {
                        txtStatus.setText("Login Cancelled");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        txtStatus.setText("Login Error: "+exception.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(!NetworkDetails.isNetworkAvailable(this))
        {
            startActivity(new Intent(LoginActivity.this,NoInternetActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
