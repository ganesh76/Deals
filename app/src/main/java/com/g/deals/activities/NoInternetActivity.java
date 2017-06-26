package com.g.deals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import  com.g.deals.R;
import com.g.deals.util.NetworkDetails;

/**
 * Created by ganesh on 22-06-2017.
 */

public class NoInternetActivity extends AppCompatActivity {
    TextView setting_tv;
    TextView try_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        setting_tv=(TextView)findViewById(R.id.nonet_set_tv);
        setting_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Settings.ACTION_SETTINGS));


            }
        });
        try_tv=(TextView)findViewById(R.id.nonet_try_tv);
        try_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkDetails.isNetworkAvailable(NoInternetActivity.this)) {

                    finish();
                }
            }
        });
    }
}
