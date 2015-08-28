package com.mokelab.demo.kiilib.page.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mokelab.demo.kiilib.MainActivity;
import com.mokelab.demo.kiilib.R;

import java.util.List;

import butterknife.ButterKnife;
import jp.fkmsoft.libs.kiilib.apis.BucketAPI;
import jp.fkmsoft.libs.kiilib.apis.KiiClause;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.apis.QueryParams;
import jp.fkmsoft.libs.kiilib.apis.QueryResult;
import jp.fkmsoft.libs.kiilib.entities.KiiBucket;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiBucket;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObject;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObjectDTO;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;

/**
 * Fragment for my memo list
 */
public class MyMemoFragment extends ListFragment {
    private static final String BUCKET_NAME = "memo";

    private BucketAPI mBucketAPI;

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

        mBucketAPI = activity.getBucketAPI();

        setListAdapter(new MyMemoAdapter(activity));

        loadMemo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region private
    private void loadMemo() {
        KiiBucket bucket = new AndroidKiiBucket(new AndroidKiiUser("me", null), BUCKET_NAME);
        QueryParams params = new QueryParams(KiiClause.all());
        params.sortByDesc("_modified");

        mBucketAPI.query(bucket, params, AndroidKiiObjectDTO.getInstance(), new BucketAPI.QueryCallback<AndroidKiiObject>() {
            @Override
            public void onSuccess(QueryResult<AndroidKiiObject> item) {
                addItems(item);
            }

            @Override
            public void onError(KiiException e) {

            }
        });
    }

    private void addItems(List<AndroidKiiObject> list) {
        MyMemoAdapter adapter = (MyMemoAdapter) getListAdapter();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
