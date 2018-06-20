package com.example.administrator.crashhandlerdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.crashhandlerdemo.base.BaseActivity;
import com.example.administrator.crashhandlerdemo.utils.PermissionUtils;

public class MainActivity extends BaseActivity {
    private String[] requestPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private PermissionUtils.PermissionTool mPermissionTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        checkPermissions();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { //针对6.0以后的版本加权限判断
            mPermissionTool = new PermissionUtils.PermissionTool(new PermissionUtils
                    .PermissionListener() {
                @Override
                public void allGranted() {
                    Log.w("tag", "所有权限都已成功获取.");
                }
            });
            mPermissionTool.checkAndRequestPermission(MainActivity.this, requestPermissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionTool.onRequestPermissionResult(this, requestCode, permissions, grantResults);
    }
}
