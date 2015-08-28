package com.mokelab.demo.kiilib;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mokelab.demo.kiilib.model.user.UserDAO;
import com.mokelab.demo.kiilib.model.user.impl.UserDAOImpl;
import com.mokelab.demo.kiilib.page.menu.MainMenuFragment;
import com.mokelab.demo.kiilib.page.title.TitleFragment;
import com.squareup.okhttp.OkHttpClient;

import jp.fkmsoft.android.framework.util.FragmentUtils;
import jp.fkmsoft.libs.kiilib.apis.BucketAPI;
import jp.fkmsoft.libs.kiilib.apis.ObjectAPI;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiAppAPI;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiBucketAPI;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiObjectAPI;
import jp.fkmsoft.libs.kiilib.client.KiiHTTPClient;
import jp.fkmsoft.libs.kiilib.entities.KiiContext;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiContext;
import jp.fkmsoft.libs.kiilib.okhttp.KiiOkHttpClient;

public class MainActivity extends AppCompatActivity {

    private KiiContext mKiiContext;
    private KiiAppAPI mAppAPI;
    private BucketAPI mBucketAPI;
    private OkHttpClient mClient;
    private Handler mHandler;
    private ObjectAPI mObjectAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClient = new OkHttpClient();
        mHandler = new Handler();

        mKiiContext = new AndroidKiiContext(Constants.APP_ID, Constants.APP_KEY, Constants.BASE_URL) {
            @Override
            public KiiHTTPClient getHttpClient() {
                return new KiiOkHttpClient(mClient, this, mHandler);
            }
        };

        mAppAPI = new KiiAppAPI(mKiiContext);
        mBucketAPI = new KiiBucketAPI(mKiiContext);
        mObjectAPI = new KiiObjectAPI(mKiiContext);

        if (savedInstanceState == null) {
            UserDAO dao = new UserDAOImpl(this);
            String token = dao.loadToken();
            if (token == null) {
                FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container,
                        TitleFragment.newInstance(), false);
            } else {
                mKiiContext.setAccessToken(token);
                FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container,
                        MainMenuFragment.newInstance(), false);
            }
        }
    }

    public KiiAppAPI getAppAPI() {
        return mAppAPI;
    }

    public BucketAPI getBucketAPI() {
        return mBucketAPI;
    }

    public ObjectAPI getObjectAPI() {
        return mObjectAPI;
    }
}
