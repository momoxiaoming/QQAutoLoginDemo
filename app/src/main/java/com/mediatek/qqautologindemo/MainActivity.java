package com.mediatek.qqautologindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andr.common.tool.apk.AppUtils;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppUtils.startApk(MainActivity.this,"com.tencent.mobileqq");
            }
        });
    }
}
