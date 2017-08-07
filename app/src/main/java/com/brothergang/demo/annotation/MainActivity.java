package com.brothergang.demo.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brothergang.demo.javalib.ClickAnnotation;
import com.brothergang.demo.module.click.OnceInit;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //类似于ButterKnife的Bind方法。初始化OnceClick
        OnceInit.once(this);
        text = (TextView) findViewById(R.id.textView);
    }

    @ClickAnnotation(R.id.btn)
    public void once() {
        //点击事件
        Log.d("tag", "onceMe:" + System.currentTimeMillis());
    }

    @ClickAnnotation(R.id.btn2)
    public void onceMe(View v) {
        ((Button) v).setText("click");
        Log.d("tag", "onceMe");
    }
}
