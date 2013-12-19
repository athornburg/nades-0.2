package com.cwat.nades;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.cwat.ui.FriendsFragment;
import com.cwat.ui.Store;
import com.cwat.util.SharedPrefsManager;

public class MainActivity extends SherlockFragmentActivity{
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    MenuList mMenuAdapter;
    private boolean userHasAuthenticated = false;
    String[] title;
    String[] subtitle;
    int[] icon;
    SherlockFragment friends = new FriendsFragment();
    SherlockFragment store = new Store();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public AccountManager manager;
    SharedPrefsManager sharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefs = new SharedPrefsManager(getSharedPreferences("Login",1));
        sharedPrefs.storeUsernameAndPassword("testing","common","gcmID");
        Toast.makeText(getApplication(),sharedPrefs.getUsername(),Toast.LENGTH_LONG).show();
        sharedPrefs.storePoints("500");
        Toast.makeText(getApplicationContext(),sharedPrefs.getPoints(),Toast.LENGTH_LONG).show();

        //Begin
        mTitle = mDrawerTitle = getTitle();
        title = new String[] {"Nearby Users","Map",
                "Attack!" };
        subtitle = new String[] {"Show players close to you","View the map",
                "Attack another player!" };
        icon = new int[] { R.drawable.map_icon, R.drawable.friends_icon,
                R.drawable.store_icon};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.listview_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        mMenuAdapter = new MenuList(MainActivity.this, title, subtitle,
                icon);
        mDrawerList.setAdapter(mMenuAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }

        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                ft.replace(R.id.content_frame,friends);
                break;

            case 1:
                Intent intent1 = new Intent(this, SherlockMapActivity.class);
                startActivity(intent1);
                break;
            case 2:
                ft.replace(R.id.content_frame, store);
                break;
        }
        ft.commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(title[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }



}
