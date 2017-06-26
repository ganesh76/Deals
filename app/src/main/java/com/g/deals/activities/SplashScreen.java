package com.g.deals.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.g.deals.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.util.ArrayList;

public class SplashScreen extends AwesomeSplash {

    SharedPreferences sharedPreferences;
    public static final String pref = "UserDetails";
    ArrayList<String> permissions_list;
    String[] perm_arr;
    @Override
    public void initSplash(ConfigSplash configSplash) {
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        setupWindowAnimations();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.coupon_svg);
        configSplash.setAnimLogoSplashDuration(1500);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);

        configSplash.setTitleSplash("Best Deals only for you!");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(23f);
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.SlideInUp);
    }

    public void repeat()
    {
        permissions_list = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            permissions_list.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions_list.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        perm_arr = new String[permissions_list.size()];
        for (int i = 0; i < permissions_list.size(); i++) {
            perm_arr[i] = permissions_list.get(i);
        }

        if (perm_arr.length > 0) {

            ActivityCompat.requestPermissions(SplashScreen.this, perm_arr, 1);
        } else {
            if(sharedPreferences.getString("loginFlag","0").equals("1"))
            {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public void animationsFinished()
    {
        permissions_list = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            permissions_list.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions_list.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        perm_arr = new String[permissions_list.size()];
        for (int i = 0; i < permissions_list.size(); i++) {
            perm_arr[i] = permissions_list.get(i);
        }

        if (perm_arr.length > 0) {

            ActivityCompat.requestPermissions(SplashScreen.this, perm_arr, 1);
        } else {
            if(sharedPreferences.getString("loginFlag","0").equals("1"))
            {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        }
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if(sharedPreferences.getString("loginFlag","0").equals("1"))
                {
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    finish();
                }
            }
        },300);*/

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int xx = 0;
        for (int i = 0; i < grantResults.length; i++)
        {
            Log.e("length", grantResults.length + "..");
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
            {
                Log.e("if", "if");
            }
            else
            {
                Log.e("else", grantResults[i] + "");
                xx++;
            }
        }

        if (xx > 0)
        {
            repeat();
        }
        else
        {
            if(sharedPreferences.getString("loginFlag","0").equals("1"))
            {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        }
        return;
    }
}
