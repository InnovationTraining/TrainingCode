package com.training.hms.ml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzer;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzerFactory;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzerSetting;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionCoordinateInput;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionResult;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewDetectResult;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.training.hms.R;
import com.training.hms.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class MLActivity extends AppCompatActivity {
    private TextView mTvResult;
    private ImageView mIvOriginalImage;
    private ImageView mIvResultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml);
        mTvResult = findViewById(R.id.text_result);
        mTvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        mIvOriginalImage = findViewById(R.id.original_image);
        mIvResultImage = findViewById(R.id.result_image);
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.text_analyse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextAnalyse();
            }
        });
        findViewById(R.id.document_skew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDocumentSkew();
            }
        });
    }

    private void onTextAnalyse() {
        mTvResult.setText("");
        // TODO 通过 MLLocalTextSetting.Factory 获取 MLLocalTextSetting 端侧文本分析器设置类。

        // TODO MLAnalyzerFactory + MLLocalTextSetting 生成文本分析器对象 MLTextAnalyzer

        mIvOriginalImage.setImageResource(R.mipmap.ml_image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ml_image);
        // TODO 通过 MLFrame + bitmap 获取 MLFrame对象

        // TODO 通过文本分析器对象 MLTextAnalyzer ，调用异步分析方法，并添加成功OnSuccessListener和失败OnFailureListener回调

        // TODO 分析成功，则调用 onDealAnalyzerText(text) 方法，解析分析的结果

        // TODO 分析失败，打印日志Log.e("---ML---", "ERROR:" + e.getMessage());
    }

    private void onDealAnalyzerText(MLText mlText) {
        if (null == mlText) {
            onInitText("文本识别结果为空");
            return;
        }
        List<MLText.Block> blockList = mlText.getBlocks();
        if (CollectionUtil.isEmpty(blockList)) {
            onInitText("文本识别结果为空");
            return;
        }
        for (MLText.Block block : blockList) {
            onInitText(block.getStringValue());
        }
    }

    private void onInitText(String string) {
        mTvResult.append(string);
    }

    private void onDocumentSkew() {
        mTvResult.setText("");
        // TODO 通过 MLDocumentSkewCorrectionAnalyzerSetting.Factory 获取 MLDocumentSkewCorrectionAnalyzerSetting 端侧文档校正设置类。

        // TODO MLDocumentSkewCorrectionAnalyzerFactory + MLDocumentSkewCorrectionAnalyzerSetting 生成文档校正分析器对象 MLDocumentSkewCorrectionAnalyzer

        mIvOriginalImage.setImageResource(R.mipmap.ml_image_skew);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ml_image_skew);

        // TODO 通过 MLFrame + bitmap 获取 MLFrame对象

        // TODO 通过文档校正分析器对象 MLDocumentSkewCorrectionAnalyzer ，调用异步分析方法，并添加成功OnSuccessListener和失败OnFailureListener回调

        // TODO 分析成功，则调用 onSkewDetect(xxx,xxx,xxx) 方法，开始稳定校正

        // TODO 分析失败，则 调用 onInitText("文档校正检测失败") 方法
    }

    private void onSkewDetect(MLDocumentSkewCorrectionAnalyzer analyzer, MLFrame frame, MLDocumentSkewDetectResult detectResult) {
        Point leftTop = detectResult.getLeftTopPosition();
        Point rightTop = detectResult.getRightTopPosition();
        Point rightBottom = detectResult.getRightBottomPosition();
        Point leftBottom = detectResult.getLeftBottomPosition();
        List<Point> coordinates = new ArrayList<>();
        coordinates.add(leftTop);
        coordinates.add(rightTop);
        coordinates.add(rightBottom);
        coordinates.add(leftBottom);
        MLDocumentSkewCorrectionCoordinateInput coordinateData = new MLDocumentSkewCorrectionCoordinateInput(coordinates);

        // asyncDocumentSkewCorrect异步调用。
        Task<MLDocumentSkewCorrectionResult> correctionTask = analyzer.asyncDocumentSkewCorrect(frame, coordinateData);
        correctionTask.addOnSuccessListener(new OnSuccessListener<MLDocumentSkewCorrectionResult>() {
            @Override
            public void onSuccess(MLDocumentSkewCorrectionResult refineResult) {
                // 校正成功。
                onInitText("\n");
                onInitText("文档校正成功");
                mIvResultImage.setImageBitmap(refineResult.getCorrected());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // 校正失败。
                onInitText("\n");
                onInitText("文档校正成功");
            }
        });
    }
}