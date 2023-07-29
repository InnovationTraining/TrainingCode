package com.training.hms.ads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;
import com.training.hms.R;

public class AdsActivity extends AppCompatActivity {

    private BannerView mBannerView;
    private RewardAd mRewardAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        // 初始化HUAWEI Ads SDK
        HwAds.init(this.getApplicationContext());
        mBannerView = findViewById(R.id.banner_view);
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.load_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取Banner广告
                onGetBannerView();
            }
        });

        findViewById(R.id.load_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载激励广告
                onLoadRewardAd();
            }
        });
    }

    /**
     * 获取Banner广告
     */
    private void onGetBannerView() {
        // 设置广告位ID和广告尺寸，"testw6vs28auh3"为测试专用的广告位ID
        // TODO 给Banner广告View mBannerView 设置广告 Id

        // TODO 给Banner广告View mBannerView 设置 Banner 大小

        // TODO 给Banner广告View mBannerView 设置轮播时间间隔为60秒

        // TODO 创建广告请求 AdParam 对象

        // TODO mBannerView加载广告

        // TODO 给 mBannerView 添加广告监听 AdListener

    }

    /**
     * 加载激励广告
     */
    private void onLoadRewardAd() {
        // TODO 创建 RewardAd 对象，并赋值给 mRewardAd

        // TODO 通过 mRewardAd 加载广告，并添加监听事件 RewardAdLoadListener

        // TODO 在事件的加载完成方法中，判断激励广告是否加载成功，如果成功，则调用显示方法

    }

    private final RewardAdStatusListener statusListener = new RewardAdStatusListener() {
        @Override
        public void onRewardAdClosed() {
            super.onRewardAdClosed();
            // 激励广告被关闭
        }

        @Override
        public void onRewardAdFailedToShow(int i) {
            super.onRewardAdFailedToShow(i);
            // 激励广告展示失败
        }

        @Override
        public void onRewardAdOpened() {
            super.onRewardAdOpened();
            // 激励广告被打开
        }

        @Override
        public void onRewarded(Reward reward) {
            super.onRewarded(reward);
            // 激励广告奖励达成
        }
    };
}