package com.sm.smmap.smmap.filepicker.activity;


import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.BusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.filepicker.BaseFileFragment;
import com.sm.smmap.smmap.filepicker.SelectOptions;
import com.sm.smmap.smmap.filepicker.adapter.FileListAdapter;
import com.sm.smmap.smmap.filepicker.loader.EssMimeTypeCollection;
import com.sm.smmap.smmap.filepicker.model.EssFile;
import com.sm.smmap.smmap.filepicker.model.FileScanActEvent;
import com.sm.smmap.smmap.filepicker.model.FileScanFragEvent;
import com.sm.smmap.smmap.filepicker.model.FileScanSortChangedEvent;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * FileTypeListFragment
 */
public class FileTypeListFragment extends BaseFileFragment implements BaseQuickAdapter.OnItemClickListener,
        EssMimeTypeCollection.EssMimeTypeCallbacks {

    private static final String ARG_FileType = "ARG_FileType";
    private static final String ARG_IsSingle = "mIsSingle";
    private static final String ARG_MaxCount = "mMaxCount";
    private static final String ARG_SortType = "mSortType";
    private static final String ARG_Loader_Id = "ARG_Loader_Id";

    private String mFileType;
    private boolean mIsSingle;
    private int mMaxCount;
    private int mSortType;
    private int mLoaderId;

    private boolean mSortTypeHasChanged = false;

    private List<EssFile> mSelectedFileList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;
    private EssMimeTypeCollection mMimeTypeCollection = new EssMimeTypeCollection();

    public FileTypeListFragment() {
    }

    public static FileTypeListFragment newInstance(String param1, boolean IsSingle, int mMaxCount, int mSortType, int loaderId) {
        FileTypeListFragment fragment = new FileTypeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FileType, param1);
        args.putBoolean(ARG_IsSingle, IsSingle);
        args.putInt(ARG_MaxCount, mMaxCount);
        args.putInt(ARG_SortType, mSortType);
        args.putInt(ARG_Loader_Id, loaderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFileType = getArguments().getString(ARG_FileType);
            mIsSingle = getArguments().getBoolean(ARG_IsSingle);
            mMaxCount = getArguments().getInt(ARG_MaxCount);
            mSortType = getArguments().getInt(ARG_SortType);
            mLoaderId = getArguments().getInt(ARG_Loader_Id);
        }
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_file_type_list;
    }

    @Override
    protected void lazyLoad() {
        mMimeTypeCollection.load(mFileType, mSortType,mLoaderId);
    }

    @Override
    protected void initUI(View view) {
        mMimeTypeCollection.onCreate(getActivity(), this);
        EventBus.getDefault().unregister(this);
        mRecyclerView = view.findViewById(R.id.rcv_file_list_scan);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FileListAdapter(new ArrayList<EssFile>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.loading_layout);
        mAdapter.setOnItemClickListener(this);
        super.initUI(view);
    }

    /**
     * ?????????Activity?????????????????????
     *
     * @param event event
     */
    @BusUtils.Subscribe
    public void onFreshCount(FileScanActEvent event) {
        mMaxCount = event.getCanSelectMaxCount();
    }

    /**
     * ?????????Activity?????????????????????
     */
    @BusUtils.Subscribe
    public void onFreshSortType(FileScanSortChangedEvent event) {
        mSortType = event.getSortType();
        if(mLoaderId == event.getCurrentItem()+EssMimeTypeCollection.LOADER_ID){
            mMimeTypeCollection.load(mFileType, mSortType,mLoaderId);
        }else {
            mSortTypeHasChanged = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            if(!isFirstLoad && mSortTypeHasChanged){
                mSortTypeHasChanged = false;
                mAdapter.setNewData(new ArrayList<EssFile>());
                mAdapter.setEmptyView(R.layout.loading_layout);
                mMimeTypeCollection.load(mFileType, mSortType,mLoaderId);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EssFile item = mAdapter.getData().get(position);
        //???????????????????????????????????????
        if (mIsSingle) {
            mSelectedFileList.add(item);
            EventBus.getDefault().post(new FileScanFragEvent(item, true));
            return;
        }
        if (mAdapter.getData().get(position).isChecked()) {
            int index = findFileIndex(item);
            if (index != -1) {
                mSelectedFileList.remove(index);
                EventBus.getDefault().post(new FileScanFragEvent(item, false));
                mAdapter.getData().get(position).setChecked(!mAdapter.getData().get(position).isChecked());
                mAdapter.notifyItemChanged(position, "");
            }
        } else {
            if (mMaxCount <= 0) {
                //??????????????????????????????
                Snackbar.make(mRecyclerView, "?????????????????????" + SelectOptions.getInstance().maxCount + "??????", Snackbar.LENGTH_SHORT).show();
                return;
            }
            mSelectedFileList.add(item);
            EventBus.getDefault().post(new FileScanFragEvent(item, true));
            mAdapter.getData().get(position).setChecked(!mAdapter.getData().get(position).isChecked());
            mAdapter.notifyItemChanged(position, "");
        }

    }

    /**
     * ??????????????????
     */
    private int findFileIndex(EssFile item) {
        for (int i = 0; i < mSelectedFileList.size(); i++) {
            if (mSelectedFileList.get(i).getAbsolutePath().equals(item.getAbsolutePath())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mMimeTypeCollection.onDestroy();
    }

    public void onFileLoad(List<EssFile> essFileList) {
        Log.i("TAG","size --> "+essFileList.size());
        mAdapter.setNewData(essFileList);
        mRecyclerView.scrollToPosition(0);
        if (essFileList.isEmpty()) {
            mAdapter.setEmptyView(R.layout.empty_file_list);
        }
    }

    @Override
    public void onFileReset() {

    }
}
