package com.shuai.androidlua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shuai.utils.AssetHelper;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("runlua");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetHelper.getInstance(getApplicationContext()).copyFileFromAssets("lua", "*");

        runLuaFile(String.format("%s/%s",  getCacheDir(), "lua/main.lua"));
    }

    public native void runLuaFile(String filePath);
}
