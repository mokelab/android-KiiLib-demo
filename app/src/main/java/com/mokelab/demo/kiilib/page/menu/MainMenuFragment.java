package com.mokelab.demo.kiilib.page.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mokelab.demo.kiilib.R;
import com.mokelab.demo.kiilib.page.my.MyMemoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * Fragment for main menu
 */
public class MainMenuFragment extends Fragment {
    @Bind(R.id.grid) GridView mGrid;

    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGrid.setAdapter(new MainMenuAdapter(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event
    @OnItemClick(R.id.grid)
    void itemClicked(AdapterView<?> grid, View item, int position, long id) {
        switch (position) {
        case 0:
            FragmentUtils.toNextFragment(getFragmentManager(), R.id.container,
                    MyMemoFragment.newInstance(), true);
            break;
        }
    }
}
