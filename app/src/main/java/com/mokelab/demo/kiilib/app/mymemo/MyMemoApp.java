package com.mokelab.demo.kiilib.app.mymemo;

import com.mokelab.demo.kiilib.app.BaseUI;

import java.util.List;

import jp.fkmsoft.android.framework.App;
import jp.fkmsoft.libs.kiilib.apis.QueryResult;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObject;

/**
 * App for MyMemo
 */
public interface MyMemoApp extends App {
    interface UI extends BaseUI {

        void showCreateProgress();

        void notifyDoneCreate(AndroidKiiObject item);

        void notifyCreateFailed();

        void notifyDoneGetAll(List<AndroidKiiObject> list);

        void notifyGetAllFailed();
    }

    void create(String title, String description);

    void getAll();
}
