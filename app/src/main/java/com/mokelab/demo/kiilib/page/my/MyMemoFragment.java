package com.mokelab.demo.kiilib.page.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mokelab.demo.kiilib.Constants;
import com.mokelab.demo.kiilib.MainActivity;
import com.mokelab.demo.kiilib.R;
import com.mokelab.demo.kiilib.app.mymemo.MyMemoApp;
import com.mokelab.demo.kiilib.app.mymemo.impl.MyMemoAppImpl;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.fkmsoft.android.framework.impl.UIHolderImpl;
import jp.fkmsoft.android.framework.util.FragmentUtils;
import jp.fkmsoft.libs.kiilib.apis.BucketAPI;
import jp.fkmsoft.libs.kiilib.apis.ObjectAPI;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObject;
import jp.fkmsoft.libs.progress.ProgressDialogFragment;

/**
 * Fragment for my memo list
 */
public class MyMemoFragment extends ListFragment implements MyMemoApp.UI {
    private static final int REQUEST_ADD = 1000;
    private static final int REQUEST_PROGRESS = 1010;

    private static final int RESUME_NO_ACTION = 0;
    private static final int RESUME_CREATE = 1;

    private MyMemoApp mApp;

    private String mInputTitle;
    private String mInputDescription;
    private int mResumeAction = RESUME_NO_ACTION;

    public static MyMemoFragment newInstance() {
        MyMemoFragment fragment = new MyMemoFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_memo_list, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

        BucketAPI bucketAPI = activity.getBucketAPI();
        ObjectAPI objectAPI = activity.getObjectAPI();
        mApp = new MyMemoAppImpl(new UIHolderImpl<>(this), bucketAPI, objectAPI);

        setListAdapter(new MyMemoAdapter(activity));

        mApp.getAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (mResumeAction) {
        case RESUME_CREATE:
            mResumeAction = RESUME_NO_ACTION;
            mApp.create(mInputTitle, mInputDescription);
            break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event
    @OnClick(R.id.button_fab)
    void addClicked() {
        FragmentUtils.toNextFragment(getFragmentManager(), R.id.container,
                AddMemoFragment.newInstance(this, REQUEST_ADD), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }
        switch (requestCode) {
        case REQUEST_ADD: {
            mInputTitle = data.getStringExtra(AddMemoFragment.EXTRA_TITLE);
            mInputDescription = data.getStringExtra(AddMemoFragment.EXTRA_DESCRIPTION);
            if (isResumed()) {
                mApp.create(mInputTitle, mInputDescription);
            } else {
                mResumeAction = RESUME_CREATE;
            }
        }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // region MyMemo UI

    @Override
    public void showCreateProgress() {
        ProgressDialogFragment dialog = ProgressDialogFragment.newInstance(this, REQUEST_PROGRESS,
                getString(R.string.create_memo), getString(R.string.creating_memo), null);
        dialog.show(getFragmentManager(), Constants.TAG_PROGRESS);
    }

    @Override
    public void notifyDoneCreate(AndroidKiiObject item) {
        MyMemoAdapter adapter = (MyMemoAdapter) getListAdapter();
        if (adapter == null) { return; }
        adapter.insert(item, 0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyCreateFailed() {
        Toast.makeText(getActivity(), R.string.create_memo_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDoneGetAll(List<AndroidKiiObject> list) {
        MyMemoAdapter adapter = (MyMemoAdapter) getListAdapter();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyGetAllFailed() {
        Toast.makeText(getActivity(), R.string.get_memo_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        FragmentUtils.dismissDialog(getFragmentManager(), Constants.TAG_PROGRESS);
    }
}
