package com.sm.smmap.smmap.Import;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;
import com.sm.smmap.smmap.JzListActivity;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Utils.BillObject;
import com.sm.smmap.smmap.Utils.ExcelUtils;
import com.sm.smmap.smmap.Utils.MyUtils;
import com.sm.smmap.smmap.filepicker.FilePicker;
import com.sm.smmap.smmap.filepicker.model.EssFile;
import com.sm.smmap.smmap.filepicker.util.Const;
import com.sm.smmap.smmap.filepicker.util.DialogUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;

public class ExcelImportActivity extends FragmentActivity implements ExcelView.View {
    ImageView add, finsh;//添加、返回按钮
    ExcelView.ExcelPresenter presenter;
    public String TAG = "ExcelImportActivity";
    int REQUESTCODE_FROM_ACTIVITY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_import);
        AndPermission
                .with(this)
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //拒绝权限
                        DialogUtil.showPermissionDialog(ExcelImportActivity.this, Permission.transformText(ExcelImportActivity.this, permissions).get(0));
                    }
                })
                .start();
        new ExcelPresent(this);
        add = findViewById(R.id.add);
        finsh = findViewById(R.id.finsh);
        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilePicker
                        .from(ExcelImportActivity.this)
                        .chooseForMimeType()
                        .setMaxCount(1)
//                .setFileTypes("png", "doc","apk", "mp3", "gif", "txt", "mp4", "zip","xls")
                        .setFileTypes("xls", "xlsx")
                        .requestCode(REQUESTCODE_FROM_ACTIVITY)
                        .start();


//                FilePicker
//                        .from(ExcelImportActivity.this)
//                        .chooseForMimeType()
//                        .setMaxCount(1)
//                        .setFileTypes("xls")
//                        .requestCode(REQUESTCODE_FROM_ACTIVITY)
//                        .start();
//                String s []=new []{};
//                new LFilePicker()
//                        .withActivity(ExcelImportActivity.this)
//                        .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
//                        .withFileFilter(new String[]{"xls"})
//                .withMaxNum(1)
//                .withChooseMode(true)
////                        .withStartPath("/storage/emulated/0/Download")
////                        .withIsGreater(false)
////                        .withFileSize(500 * 1024)
//                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUESTCODE_FROM_ACTIVITY) {//解析xls文件
            ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
            StringBuilder builder = new StringBuilder();
            EssFile essFile = essFileList.get(0);
            for (EssFile file :
                    essFileList) {
                builder.append(file.getAbsolutePath());
            }
            File file = new File(essFile.getAbsolutePath());
            Log.d(TAG, "onActivityResult: "+file.getPath());
            List<BillObject> billObjects = ExcelUtils.read2DB(file, ExcelImportActivity.this);
            Log.d(TAG, "AAonActivityResult: " + builder.toString() + billObjects.toString());

            if (billObjects.size() > 0 && billObjects != null) {
                List<BillObject> billObjects1 = MyUtils.removeDuplicate(billObjects);//去重
                Log.d(TAG, "billObjectsonActivityResult: " + billObjects1.toString());
                presenter.Querys(billObjects1, ExcelImportActivity.this);
            }

        } else {
            ToastUtils.showToast("文件错误,请选择正确的Excel文件");
        }
    }


    @Override
    public void finishs() {
        finish();
        Log.d(TAG, "finishs: ");
    }

    @Override
    public void QueryShow() {
//        com.blankj.utilcode.util.ToastUtils.showShort("完事");
        startActivity(new Intent(ExcelImportActivity.this,JzListActivity.class));
        finish();
    }

    @Override
    public void setPresenter(ExcelView.ExcelPresenter presenter) {
        this.presenter = presenter;
    }
}
