package com.johneas.android.movie.view.movies.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johneas.android.movie.databinding.FragmentDetailViewBinding;
import com.johneas.android.movie.view.common.base.BaseFragment;

public class MovieDetailFragment extends BaseFragment
		implements MovieDetailContract.view{

	private static final String TAG = MovieDetailFragment.class.getSimpleName();
	public static final String INTENT_MOVIE_ID = "movie_id";
	private int movieId;
	private FragmentDetailViewBinding binding;

	public MovieDetailFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		binding = FragmentDetailViewBinding.inflate(inflater);
		binding.setViewModel(new MoviesDetailViewModel());
		binding.setPresenter(new MovieDetailPresenter(this));

		Intent intent = getActivity().getIntent();
		if (intent != null && intent.hasExtra(INTENT_MOVIE_ID)) {
			movieId = intent.getIntExtra(INTENT_MOVIE_ID, 0);
			binding.getPresenter().loadMovieDetails(movieId);
		}

		return binding.getRoot();
	}


	@Override
	public MoviesDetailViewModel getViewModel() {
		return binding.getViewModel();
	}

	@Override
	public void setViewModel(MoviesDetailViewModel viewModel) {
		binding.setViewModel(viewModel);
	}

	@Override
	public void printLog(String message) {

	}
}
