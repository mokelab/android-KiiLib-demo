package com.mokelab.demo.kiilib.model.user.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mokelab.demo.kiilib.model.user.UserDAO;

/**
 * Implementation
 */
public class UserDAOImpl implements UserDAO {
    private static final String PREF_NAME = "user";

    private static final String KEY_TOKEN = "a";

    private final Context mContext;

    public UserDAOImpl(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences pref = getPref();
        pref.edit().putString(KEY_TOKEN, token).apply();
    }

    @Override
    public String loadToken() {
        SharedPreferences pref = getPref();
        return pref.getString(KEY_TOKEN, null);
    }

    private SharedPreferences getPref() {
        return mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}
