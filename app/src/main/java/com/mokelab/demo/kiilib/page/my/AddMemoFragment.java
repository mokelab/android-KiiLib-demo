package com.mokelab.demo.kiilib.page.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mokelab.demo.kiilib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment for add memo
 */
public class AddMemoFragment extends Fragment {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "desc";

    @Bind(R.id.edit_title) EditText mTitleEdit;
    @Bind(R.id.edit_desc) EditText mDescEdit;

    public static AddMemoFragment newInstance(Fragment target, int requestCode) {
        AddMemoFragment fragment = new AddMemoFragment();
        fragment.setTargetFragment(target, requestCode);
        
        Bundle args = new Bundle();
        fragment.setArguments(args);
        
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_my_memo, container, false);
    
        ButterKnife.bind(this, root);
    
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    
        ButterKnife.unbind(this);
    }

    // UI event

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_save:
            submit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        Fragment target = getTargetFragment();
        if (target == null) { return; }

        String title = mTitleEdit.getText().toString();
        String desc = mDescEdit.getText().toString();

        if (TextUtils.isEmpty(title)) {
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);

        getFragmentManager().popBackStack();
    }
}
