package com.cwat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * @author Alex Thornburg and Cooper Wickum
 */
public class SherlockMapFragment extends SupportMapFragment {
    public SherlockMapFragment() {

        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstance) {

        View layout = super.onCreateView(inflater, view, savedInstance);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((ViewGroup) layout).addView(frameLayout, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return layout;
    }
}
