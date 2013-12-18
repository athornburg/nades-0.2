package com.cwat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.cwat.nades.R;

/**
 * @author Alex Thornburg and Cooper Wickum
 */
public class FriendsFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.friends, container, false);
        return rootView;


    }
}
