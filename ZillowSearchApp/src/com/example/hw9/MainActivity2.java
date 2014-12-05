package com.example.hw9;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.conn.LoggingSessionInputBuffer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends FragmentActivity implements
		ActionBar.TabListener {
	ImageButton imageButton;
	String Value1 = "";
	private ViewPager viewPager;
	private ActionBar actionBar;
	private TabPagerAdapter tabPagerAdapter;
	private static final String TAG = "TAG_MyActivity";
	ArrayList<String> getPool = new ArrayList<String>();
	TextView view;
	private String[] tabs = { "BASIC INFO", "HISTORICAL ZESTIMATE" };
	// private SwipeTabFragment mainFragment;
	Activity thisActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main2);

		Intent intent = getIntent();
		getPool = intent.getStringArrayListExtra("Value1");

		viewPager = (ViewPager) findViewById(R.id.pager);
		tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(tabPagerAdapter);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			/**
			 * on swipe select the respective tab
			 * */
			@Override
			public void onPageSelected(int position) {

				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main2, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_back) {

			finish();
		}
		return true;
	}

	public void click(View v) {
		Toast.makeText(MainActivity2.this, "ImageButton is clicked!",
				Toast.LENGTH_SHORT).show();
	}

	public void share(View v) {

		// Toast.makeText(MainActivity2.this,"ImageButton is clicked!",
		// Toast.LENGTH_SHORT).show();
		// SwipeTabFragment function=new SwipeTabFragment();
		// function. publishStory();

	}

	public ArrayList<String> getMyData() {

		return getPool;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}
