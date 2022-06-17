package com.sm.smmap.smmap.filepicker.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.filepicker.model.EssFile;

import java.util.List;

/**
 * 图片浏览界面
 */
public class PicturesViewActivity extends AppCompatActivity {

    /*要浏览的图片列表*/
    private List<EssFile> mViewPicturesList;
    /*当前所处位置*/
    private int mCurPosition;
    /*是否是预览图片选择中的图片*/
    private boolean mIsViewForSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_detail);
    }
}
