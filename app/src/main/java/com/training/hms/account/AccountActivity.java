package com.training.hms.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.api.entity.common.CommonConstant;
import com.training.hms.R;

public class AccountActivity extends AppCompatActivity {

    // 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
    private AccountAuthService mAccountAuthService;

    // 华为帐号登录授权参数
    private AccountAuthParams mAccountAuthParams;

    private TextView mTvResult;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // TODO 通过 AccountAuthManager.parseAuthResultFromIntent 方法，解析 result 中 intent数据

            // TODO 解析出的数据调用 isSuccessful() 方法，判断是否登录成功

            // TODO 如果登录成功，则通过解析出的数据，获取 AuthAccount 数据对象，并调用 onDealAuthAccount(xx)方法

            // TODO 如果登录失败，则调用 onInitText("登录失败：")
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mTvResult = findViewById(R.id.text_result);
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.id_token_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIdTokenSignIn();
            }
        });
        findViewById(R.id.silent_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSilentSignIn();
            }
        });
        findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOut();
            }
        });
        findViewById(R.id.cancel_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelAuthorization();
            }
        });
    }

    private void onIdTokenSignIn() {
        // TODO 通过 AccountAuthParamsHelper 创建账号 IdToken 授权模式的参数对象 mAccountAuthParams

        // TODO 通过 AccountAuthManager.getService 方法获取 AccountAuthService 对象

        // TODO 使用 launcher.launch 方法，调用启动授权页面，参数为 AccountAuthService 中登录的 Intent 对象


    }

    private void onSilentSignIn() {
        // TODO 通过 AccountAuthParamsHelper 创建账号 IdToken 授权模式的参数对象 mAccountAuthParams

        // TODO 通过 AccountAuthManager.getService方 法获取 AccountAuthService 对象

        // TODO 通过 AccountAuthService 调用静默登录方法，并获取返回结果

        // TODO 给返回结果添加成功 OnSuccessListener 和失败 OnFailureListener 的回调方法

        // TODO 如果成功，则在成功的回调方法中，调用 onDealAuthAccount(xx) 方法

        // TODO 如果失败，则调用 onIdTokenSignIn() 方法

    }

    private void onSignOut() {
        if (null == mAccountAuthService) {
            return;
        }
        // TODO 通过 mAccountAuthService 调用退出登录方法

        // TODO 添加成功和失败的回调

        // TODO 如果成功，则调用 onInitText("退出登录成功")

        // TODO 如果失败，则调用 onInitText("退出登录成功")

    }

    private void onCancelAuthorization() {
        if (null == mAccountAuthService) {
            return;
        }
        // TODO 通过 mAccountAuthService 调用取消授权方法

        // TODO 添加成功和失败的回调

        // TODO 如果成功，则调用 onInitText("取消授权成功")

        // TODO 如果失败，则调用 onInitText("取消授权失败")

    }

    private void onDealAuthAccount(AuthAccount authAccount) {
        onInitText("IDToken模式登录成功");
        onInitText("昵称：" + authAccount.getDisplayName());
        onInitText("邮箱：" + authAccount.getEmail());
        onInitText("头像：" + authAccount.getAvatarUriString());
        Log.e("----Account----", authAccount.getAvatarUriString());
        onInitText("IDToken：" + authAccount.getIdToken());
    }

    private void onInitText(String string) {
        mTvResult.append(string);
        mTvResult.append("\n");
    }
}