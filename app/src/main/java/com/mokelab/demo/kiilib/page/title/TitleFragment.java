package com.mokelab.demo.kiilib.page.title;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mokelab.demo.kiilib.Constants;
import com.mokelab.demo.kiilib.MainActivity;
import com.mokelab.demo.kiilib.R;
import com.mokelab.demo.kiilib.app.login.LoginApp;
import com.mokelab.demo.kiilib.app.login.impl.LoginAppImpl;
import com.mokelab.demo.kiilib.app.signup.SignupApp;
import com.mokelab.demo.kiilib.app.signup.impl.SignupAppImpl;
import com.mokelab.demo.kiilib.model.user.impl.UserDAOImpl;
import com.mokelab.demo.kiilib.page.menu.MainMenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.fkmsoft.android.framework.impl.UIHolderImpl;
import jp.fkmsoft.android.framework.util.FragmentUtils;
import jp.fkmsoft.libs.kiilib.apis.KiiException;
import jp.fkmsoft.libs.kiilib.apis.impl.KiiAppAPI;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiUser;
import jp.fkmsoft.libs.progress.ProgressDialogFragment;

/**
 * Fragment for title page
 */
public class TitleFragment extends Fragment implements LoginApp.UI, SignupApp.UI {
    private static final int REQUEST_PROGRESS = 1000;

    @Bind(R.id.edit_login_email) EditText mLoginEmail;
    @Bind(R.id.edit_login_password) EditText mLoginPassword;
    @Bind(R.id.edit_signup_email) EditText mSignupEmail;
    @Bind(R.id.edit_signup_password) EditText mSignupPassword;

    private LoginApp mLoginApp;
    private SignupApp mSignupApp;

    public static TitleFragment newInstance() {
        TitleFragment fragment = new TitleFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_title, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        KiiAppAPI appAPI = activity.getAppAPI();
        mLoginApp = new LoginAppImpl(new UIHolderImpl<>(this), appAPI, new UserDAOImpl(activity));
        mSignupApp = new SignupAppImpl(new UIHolderImpl<>(this), appAPI);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event
    @OnClick(R.id.button_login)
    void loginClicked() {
        mLoginApp.login(mLoginEmail.getText().toString(), mLoginPassword.getText().toString());
    }

    @OnClick(R.id.button_signup)
    void signupClicked() {
        mSignupApp.signup(mSignupEmail.getText().toString(), mSignupPassword.getText().toString());
    }


    // region Login UI

    @Override
    public void showLoginProgress() {
        ProgressDialogFragment dialog = ProgressDialogFragment.newInstance(this, REQUEST_PROGRESS,
                getString(R.string.login), getString(R.string.login), null);
        dialog.show(getFragmentManager(), Constants.TAG_PROGRESS);
    }

    @Override
    public void notifyDoneLogin(AndroidKiiUser kiiUser) {
        FragmentUtils.toNextFragment(getFragmentManager(), R.id.container,
                MainMenuFragment.newInstance(), false);
    }

    @Override
    public void showLoginError(KiiException e) {
        Toast.makeText(getActivity(), e.getBody().toString(), Toast.LENGTH_LONG).show();
        Log.v("Login", "Error " + e.getBody().toString());
    }

    @Override
    public void hideProgress() {
        FragmentUtils.dismissDialog(getFragmentManager(), Constants.TAG_PROGRESS);
    }

    @Override
    public void showSignupProgress() {
        ProgressDialogFragment dialog = ProgressDialogFragment.newInstance(this, REQUEST_PROGRESS,
                getString(R.string.signup), getString(R.string.signup), null);
        dialog.show(getFragmentManager(), Constants.TAG_PROGRESS);
    }

    @Override
    public void notifyDoneSignup(AndroidKiiUser kiiUser) {
        Log.v("Signup", "Done!");
    }

    @Override
    public void showSignupError(KiiException e) {
        Toast.makeText(getActivity(), e.getBody().toString(), Toast.LENGTH_LONG).show();
        Log.v("Signup", "Error " + e.getBody().toString());
    }
}
