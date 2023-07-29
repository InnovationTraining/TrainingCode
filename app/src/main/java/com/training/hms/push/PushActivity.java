package com.training.hms.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.training.hms.R;

public class PushActivity extends AppCompatActivity {
    private TextView mTvResult;
    private TokenReceiver mTokenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        mTvResult = findViewById(R.id.text_result);
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        initClickListener();

        mTokenReceiver = new TokenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.training.hms.push.onNewToken");
        LocalBroadcastManager.getInstance(this).registerReceiver(mTokenReceiver, filter);
    }

    private void initClickListener() {
        findViewById(R.id.get_token).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetToken();
            }
        });

    }

    private void onGetToken() {
        new Thread() {
            @Override
            public void run() {
                try {
                    // TODO 通过 AGConnectInstance 从 agconnect-services.json 中获取 APP_ID

                    // TODO 通过 HmsInstanceId 获取 Token，其中的参数为上一步中获取的 APP_ID 值，以及固定标识 "HCM"
                    String token = "";
                    Log.e("--TrainingPush--", "token: " + token);

                    // 判断token是否为空
                    if (!TextUtils.isEmpty(token)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onInitText(token);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("--TrainingPush--", "获取Token失败：" + e);
                }
            }
        }.start();
    }

    private void onInitText(String string) {
        mTvResult.append(string);
        mTvResult.append("\n");
    }

    private class TokenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            if ("com.training.hms.push.onNewToken".equals(intent.getAction())) {
                String token = intent.getStringExtra("token");
                Log.e("--TrainingPush--", "token: " + token);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onInitText(token);
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mTokenReceiver);
    }
}