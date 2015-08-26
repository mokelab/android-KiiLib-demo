package com.mokelab.demo.kiilib.app.login.impl;

import com.mokelab.demo.kiilib.app.login.LoginApp;

import jp.fkmsoft.android.framework.UIHolder;
import jp.fkmsoft.android.framework.base.BaseApp;
import jp.fkmsoft.libs.kiilib.apis.AppAPI;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiAppAPI;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUserDTO;

/**
 * Implementation
 */
public class LoginAppImpl extends BaseApp<LoginApp.UI> implements LoginApp {

    private final KiiAppAPI mAppAPI;

    public LoginAppImpl(UIHolder<UI> uiHolder, KiiAppAPI appAPI) {
        super(uiHolder);
        mAppAPI = appAPI;
    }

    @Override
    public void login(String email, String password) {
        UI ui = mUIHolder.get();
        if (ui == null) { return; }

        ui.showLoginProgress();
        mAppAPI.loginAsUser(email, password, AndroidKiiUserDTO.getInstance(), new AppAPI.LoginCallback<AndroidKiiUser>() {
            @Override
            public void onSuccess(String token, AndroidKiiUser kiiUser) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }

                ui.hideProgress();
                ui.notifyDoneLogin(kiiUser);
            }

            @Override
            public void onError(KiiException e) {
                UI ui = mUIHolder.get();
                if (ui == null) { return; }
                ui.hideProgress();

                ui.showLoginError(e);
            }
        });
    }
}
