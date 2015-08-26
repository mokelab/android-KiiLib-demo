package com.mokelab.demo.kiilib.app.signup;

import com.mokelab.demo.kiilib.app.BaseUI;

import jp.fkmsoft.android.framework.App;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;

/**
 * Signup feature
 */
public interface SignupApp extends App {
    interface UI extends BaseUI {

        void showSignupProgress();

        void notifyDoneSignup(AndroidKiiUser kiiUser);

        void showSignupError(KiiException e);
    }

    void signup(String email, String password);
}
