package com.sm.smmap.smmap.Import;

import android.content.Context;

import com.sm.smmap.smmap.Base.BasePresenter;
import com.sm.smmap.smmap.Base.BaseView;
import com.sm.smmap.smmap.Utils.BillObject;

import java.util.List;

public class ExcelView {
    interface View extends BaseView<ExcelPresenter> {//更新

        void finishs();

        void QueryShow();
    }

    interface ExcelPresenter extends BasePresenter {//使用

        void finish();

        void Querys(List<BillObject> billObjectList, Context context);
    }
}
