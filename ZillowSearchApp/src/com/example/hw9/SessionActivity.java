package com.example.hw9;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


public class SessionActivity extends FragmentActivity {
	
	private SwipeTabFragment mainFragment;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        if (savedInstanceState == null) {
        	// Add the fragment on initial activity setup
        	mainFragment = new SwipeTabFragment();
            getSupportFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, mainFragment)
            .commit();
        } else {
        	// Or set the fragment from restored state info
        	mainFragment = (SwipeTabFragment) getSupportFragmentManager()
        	.findFragmentById(android.R.id.content);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
}