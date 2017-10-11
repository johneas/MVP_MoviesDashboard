package com.johneas.android.movie.view.movies.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.johneas.android.movie.R;
import com.johneas.android.movie.view.common.base.BaseActivity;

public class MovieDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MovieDetailFragment detailFragment = new MovieDetailFragment();

		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction()
				.add(R.id.fragment_container, detailFragment)
				.commit();
	}

}
