package com.training.hms.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStates;
import com.huawei.hms.location.SettingsClient;
import com.training.hms.R;
import com.training.hms.util.CollectionUtil;

import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private TextView mTvResult;
    // 声明fusedLocationProviderClient对象
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null) {
                onDealLocationInfo(locationResult);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mTvResult = findViewById(R.id.text_result);
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        initClickListener();
        initPermission();
    }

    private void initClickListener() {
        findViewById(R.id.check_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckLocation();
            }
        });
        findViewById(R.id.get_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetLocation();
            }
        });

    }

    private void initPermission() {
        // Android SDK<=28 所需权限动态申请
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            // Android SDK>28 所需权限动态申请，需添加“android.permission.ACCESS_BACKGROUND_LOCATION”权限
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }
    }

    private void onCheckLocation() {
        // TODO 通过 LocationServices 获取 SettingsClient 对象

        // TODO 创建定位设置请求构造器 LocationSettingsRequest.Builder

        // TODO 给构造器设置定位请求对象 LocationRequest

        // TODO 通过构造器获取定位请求对象 LocationSettingsRequest

        // TODO 通过 SettingsClient 调用检查设备定位设置，并添加成功 OnSuccessListener 和失败 OnFailureListener 回调

        // TODO 在成功方法中，通过 locationSettingsStates.isLocationUsable() 判断定位是否打开

        // TODO 在失败方法中，调用 onInitText("定位没有打开,请到设置中打开") 方法

    }

    private void onGetLocation() {
        // TODO 通过 LocationServices 实例化 mFusedLocationProviderClient 对象

        // TODO 创建定位请求参数 LocationRequest

        // TODO 定位请求参数设置定位类型

        // TODO 定位请求参数设置位置更新的间隔（单位：毫秒）为 10000 即 10s

        // TODO 通过实例化的对象 mFusedLocationProviderClient ，调用启动定位方法，添加回调 mLocationCallback，并添加结果成功 OnSuccessListener 和失败 OnFailureListener 回调

        // TODO 在成功方法中，调用 onInitText("启动定位成功，开始获取定位信息")

        // TODO 在失败方法中，调用 onInitText("启动定位失败")

    }

    private void onDealLocationInfo(LocationResult locationResult) {
        if (null == locationResult) {
            onInitText("获取定位信息错误");
            return;
        }

        List<Location> locationList = locationResult.getLocations();
        if (CollectionUtil.isEmpty(locationList)) {
            onInitText("获取定位信息为空");
            return;
        }
        for (Location location : locationList) {
            onInitText("纬度:" + location.getLatitude());
            onInitText("经度:" + location.getLongitude());
        }
    }

    private void onInitText(String string) {
        mTvResult.append(string);
        mTvResult.append("\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mFusedLocationProviderClient) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                    // 停止位置更新成功监听回调
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    })
                    // 停止位置更新失败监听回调
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                        }
                    });
            mFusedLocationProviderClient.disableBackgroundLocation();
        }
    }
}