package com.example.hw9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.hw9.*;

public class TabPagerAdapter extends FragmentPagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Bundle bundle = new Bundle();
		int tab;
		switch (index) {
		case 0:
			tab = 0;
//			bundle.putString("case0", "case1");
			break;

		case 1:
			tab = 1;
			break;

		default:
			tab = 0;
			break;
		}
		SwipeTabFragment swipeTabFragment = new SwipeTabFragment();
		SwipeTabFragment2 swipeTabFragment2 = new SwipeTabFragment2();
		if (tab == 0) {
			swipeTabFragment.setArguments(bundle);
			return swipeTabFragment;

		} else if (tab == 1) {
			return swipeTabFragment2;

		} else {
			return swipeTabFragment;

		}

	}

	@Override
	public int getCount() {
		return 2;
	}
}