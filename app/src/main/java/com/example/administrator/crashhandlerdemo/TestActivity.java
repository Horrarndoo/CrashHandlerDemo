package com.example.administrator.crashhandlerdemo;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.crashhandlerdemo.base.BaseActivity;
import com.example.administrator.crashhandlerdemo.manager.AppManager;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new IllegalStateException("This is test exception.");
            }
        });

        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishCurrentActivity();
            }
        });
    }
}
