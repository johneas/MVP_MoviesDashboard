package com.johneas.android.movie.view.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.johneas.android.movie.R;
import com.johneas.android.movie.view.common.base.BaseActivity;
import com.johneas.android.movie.view.movies.list.MoviesListFragment;

public class MainActivity extends BaseActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fragmentManager = getSupportFragmentManager();
		MoviesListFragment listFragment = new MoviesListFragment();

		listFragment = new MoviesListFragment();
		fragmentManager.beginTransaction()
				.add(R.id.fragment_container, listFragment)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}



}
