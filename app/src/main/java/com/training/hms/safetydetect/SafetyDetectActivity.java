package com.training.hms.safetydetect;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.api.entity.safetydetect.SysIntegrityRequest;
import com.huawei.hms.support.api.entity.safetydetect.SysIntegrityResp;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.training.hms.R;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SafetyDetectActivity extends AppCompatActivity {
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_detect);
        mTvResult = findViewById(R.id.text_result);
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.sysIntegrity_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSysIntegrity();
            }
        });
    }

    private void onSysIntegrity() {
        // TODO 通过 SafetyDetect 获取 SafetyDetectClient 的对象

        // TODO 创建 SysIntegrityRequest 请求参数对象

        // 一个nonce值只能被使用一次。,nonce值必须为16至66字节之间。推荐的做法是从发送到您的服务器的数据中派生nonce值。
        // TODO 给请求参数设置 Nonce 值，值获取方式为调用 getDetectNonce() 方法

        // TODO 此通过 AGConnectInstance 从 agconnect-services.json 中获取 APP_ID

        // TODO 给求参数设置签名算法 "RS256"

        // TODO 通过获取到的 SafetyDetectClient 的对象，调用 sysIntegrity 方法，进行系统完整性检测

        // TODO 给检测结果对象，添加成功 OnSuccessListener 和失败 OnFailureListener 回调

        // TODO 测结成功，则调用验证系统完整性检测结果方法 onCheckSysIntegrityResult(xxx);

        // TODO 测结失败，打印日志Log.e("---SafetyDetect---", "ERROR:" + e.getMessage());

    }

    private byte[] getDetectNonce() {
        byte[] nonce = new byte[24];
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            random.nextBytes(nonce);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return nonce;
    }

    /**
     * 验证系统完整性检测结果
     */
    private void onCheckSysIntegrityResult(SysIntegrityResp response) {
        String jwsStr = response.getResult();
        onInitText("检测结果数据：");
        onInitText(jwsStr);
        String[] jwsSplit = jwsStr.split("\\.");
        String jwsPayloadStr = jwsSplit[1];
        String payloadDetail = new String(Base64.decode(jwsPayloadStr.getBytes(StandardCharsets.UTF_8), Base64.URL_SAFE), StandardCharsets.UTF_8);
        onInitText("解析结果数据：");
        onInitText(payloadDetail);
        try {
            JSONObject jsonObject = new JSONObject(payloadDetail);
            boolean basicIntegrity = jsonObject.getBoolean("basicIntegrity");
            if (basicIntegrity) {
                onInitText("系统完整性检测结果：安全");
            } else {
                onInitText("系统完整性检测结果：不安全");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onInitText(String string) {
        mTvResult.append(string);
        mTvResult.append("\n");
    }
}