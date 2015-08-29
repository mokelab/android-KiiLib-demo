package com.mokelab.demo.kiilib.app.mymemo.impl;

import com.mokelab.demo.kiilib.app.mymemo.MyMemoApp;
import com.mokelab.demo.kiilib.model.memo.Memo;

import org.json.JSONException;
import org.json.JSONObject;

import jp.fkmsoft.android.framework.UIHolder;
import jp.fkmsoft.android.framework.base.BaseApp;
import jp.fkmsoft.libs.kiilib.apis.BucketAPI;
import jp.fkmsoft.libs.kiilib.apis.KiiClause;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.apis.ObjectAPI;
import jp.fkmsoft.libs.kiilib.apis.QueryParams;
import jp.fkmsoft.libs.kiilib.apis.QueryResult;
import jp.fkmsoft.libs.kiilib.entities.KiiBucket;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiBucket;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObject;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObjectDTO;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;

/**
 * Implementation
 */
public class MyMemoAppImpl extends BaseApp<MyMemoApp.UI> implements MyMemoApp {
    private static final String BUCKET_NAME = "memo";

    private final BucketAPI mBucketAPI;
    private final ObjectAPI mObjectAPI;

    public MyMemoAppImpl(UIHolder<UI> uiHolder, BucketAPI bucketAPI, ObjectAPI objectAPI) {
        super(uiHolder);
        mBucketAPI = bucketAPI;
        mObjectAPI = objectAPI;
    }

    @Override
    public void create(final String title, final String description) {
        UI ui = mUIHolder.get();
        if (ui == null) { return; }

        JSONObject json = new JSONObject();
        try {
            json.put(Memo.FIELD_TITLE, title);
            json.put(Memo.FIELD_DESCRIPTION, description);
            json.put(Memo.FIELD_DONE, false);
        } catch (JSONException ignore) {
            // nop
        }

        ui.showCreateProgress();

        KiiBucket bucket = new AndroidKiiBucket(new AndroidKiiUser("me", null), BUCKET_NAME);
        mObjectAPI.create(bucket, json, AndroidKiiObjectDTO.getInstance(), new ObjectAPI.ObjectCallback<AndroidKiiObject>() {
            @Override
            public void onSuccess(AndroidKiiObject item) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }

                ui.hideProgress();
                try {
                    item.put(Memo.FIELD_TITLE, title);
                    item.put(Memo.FIELD_DESCRIPTION, description);
                } catch (JSONException ignore) {
                    // nop
                }
                ui.notifyDoneCreate(item);
            }

            @Override
            public void onError(KiiException e) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }

                ui.hideProgress();

                ui.notifyCreateFailed();
            }
        });
    }

    @Override
    public void getAll() {
        KiiBucket bucket = new AndroidKiiBucket(new AndroidKiiUser("me", null), BUCKET_NAME);
        QueryParams params = new QueryParams(KiiClause.equals(Memo.FIELD_DONE, false));
        params.sortByDesc("_modified");

        mBucketAPI.query(bucket, params, AndroidKiiObjectDTO.getInstance(), new BucketAPI.QueryCallback<AndroidKiiObject>() {
            @Override
            public void onSuccess(QueryResult<AndroidKiiObject> item) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }

                ui.notifyDoneGetAll(item);
            }

            @Override
            public void onError(KiiException e) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }

                ui.notifyGetAllFailed();
            }
        });
    }
}
