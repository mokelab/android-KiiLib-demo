package com.mokelab.demo.kiilib.app.signup.impl;

import com.mokelab.demo.kiilib.app.login.LoginApp;
import com.mokelab.demo.kiilib.app.signup.SignupApp;

import jp.fkmsoft.android.framework.UIHolder;
import jp.fkmsoft.android.framework.base.BaseApp;
import jp.fkmsoft.libs.kiilib.apis.AppAPI;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.apis.SignupInfo;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiAppAPI;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUserDTO;

/**
 * Implementation
 */
public class SignupAppImpl extends BaseApp<SignupApp.UI> implements SignupApp {

    private final KiiAppAPI mAppAPI;

    public SignupAppImpl(UIHolder<UI> uiHolder, KiiAppAPI appAPI) {
        super(uiHolder);
        mAppAPI = appAPI;
    }

    @Override
    public void signup(String email, String password) {
        SignupApp.UI ui = mUIHolder.get();
        if (ui == null) { return; }

        SignupInfo info = SignupInfo.UserWithEmail(email);

        ui.showSignupProgress();
        mAppAPI.signup(info, password, AndroidKiiUserDTO.getInstance(), new AppAPI.SignupCallback<AndroidKiiUser>() {
            @Override
            public void onSuccess(AndroidKiiUser kiiUser) {
                SignupApp.UI ui = mUIHolder.get();
                if (ui == null) {
                    return;
                }

                ui.hideProgress();
                ui.notifyDoneSignup(kiiUser);
            }

            @Override
            public void onError(KiiException e) {
                SignupApp.UI ui = mUIHolder.get();
                if (ui == null) {
                    return;
                }
                ui.hideProgress();

                ui.showSignupError(e);
            }
        });
    }
}
