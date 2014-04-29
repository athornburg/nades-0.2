package com.cwat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.cwat.nades.MainActivity;
import com.cwat.nades.MenuList;
import com.cwat.nades.R;

/**
 * @author Alex Thornburg and Cooper Wickum
 */

//Should this really be called "Store" when it's
//pretty much just a list of attacks? -- Cooper

public class Store extends SherlockFragment {

    ListView mWeaponList;
    private TextView mWeaponName;
    private TextView mWeaponDesc;
    String[] title;
    String[] subtitle;
    int[] icon;
    String[] description;
    private MenuList mMenuAdapter;
    int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.store, container, false);

        mWeaponList = (ListView) rootView.findViewById(R.id.listview_weapons);
        mWeaponName = (TextView) rootView.findViewById(R.id.weaponName);
        mWeaponDesc = (TextView) rootView.findViewById(R.id.weaponDesc);

        title = new String[] {"Mine"};
        subtitle = new String[] {"Place a mine at your current location."};
        description = new String[] {
                "Place a mine at your current location. If other players step on it, you get points."
        };
        icon = new int[] { R.drawable.mine_icon };

        mMenuAdapter = new MenuList(this.getActivity().getBaseContext(), title, subtitle,
                icon);
        mWeaponList.setAdapter(mMenuAdapter);
        mWeaponList.setOnItemClickListener(new DrawerItemClickListener());

        rootView.findViewById(R.id.useWeapon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropMine();
            }
        });


        return rootView;

    }

    //listView.setOnItemClickListener(mMessageClickedHandler);

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
            pos = position;
        }
    }

    private void selectItem(int position) {

        mWeaponName.setText(title[position].toString());
        mWeaponDesc.setText(description[position].toString());

    }

    private void dropMine() {

        if (!mWeaponName.getText().equals(""))
        {
            switch (pos) {
                case 0: //Drop a mine

                    Toast.makeText(getActivity().getBaseContext(),
                            "DROP LAND MINE",
                            Toast.LENGTH_SHORT).show();
                    
                    break;
            }
        }
        else
        {
            Toast.makeText(getActivity().getBaseContext(),
                    "Select a weapon first.",
                    Toast.LENGTH_SHORT).show();
        }

    }



}

