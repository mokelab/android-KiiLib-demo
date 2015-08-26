package com.mokelab.demo.kiilib.app.login;

import com.mokelab.demo.kiilib.app.BaseUI;

import jp.fkmsoft.android.framework.App;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;

/**
 * Login feature
 */
public interface LoginApp extends App {
    interface UI extends BaseUI {
        void showLoginProgress();

        void notifyDoneLogin(AndroidKiiUser kiiUser);

        void showLoginError(KiiException e);
    }

    void login(String email, String password);

}
