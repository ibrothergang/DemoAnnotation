package com.brothergang.demo.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.brothergang.demo.javalib.ClickAnnotation;
import com.brothergang.demo.module.click.AnnotationMgr;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //类似于ButterKnife的Bind方法。
        AnnotationMgr.init(this);
    }

    @ClickAnnotation(id=R.id.btn, toast="点击了第一个按钮")
    public void buttonClick01() {
        Log.d(TAG, "buttonClick01");
    }

    @ClickAnnotation(id=R.id.btn2, toast="点击了第二个按钮")
    public void buttonClick02(View v) {
        Log.d(TAG, "buttonClick02");
    }
}
