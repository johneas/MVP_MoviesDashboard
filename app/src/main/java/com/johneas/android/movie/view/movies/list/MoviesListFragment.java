package com.johneas.android.movie.view.movies.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.johneas.android.movie.R;
import com.johneas.android.movie.databinding.FragmentGridViewBinding;
import com.johneas.android.movie.view.common.base.BaseFragment;
import com.johneas.android.movie.view.main.MainActivity;
import com.johneas.android.movie.view.movies.detail.MovieDetailActivity;
import com.johneas.android.movie.view.movies.detail.MovieDetailFragment;
import com.johneas.android.movie.view.movies.list.adapter.MovieAdapter;
import com.johneas.android.movie.view.movies.list.enums.SortMovieType;
import com.johneas.android.movie.view.common.interfaces.ListItemClickListener;
import com.johneas.android.movie.view.common.utils.PaginationScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesListFragment extends BaseFragment
		implements MovieListContract.view, OnGlobalLayoutListener {

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;

	private static final String TAG = MoviesListFragment.class.getSimpleName() ;

	private GridLayoutManager layoutManager;
	private SearchView searchView;

	private boolean isLoading = false;
	private boolean isLastPage = false;
	private int totalPages;

	private FragmentGridViewBinding binding;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		binding = FragmentGridViewBinding.inflate(inflater);
		binding.setViewModel(new MovieListViewModel());
		binding.setPresenter(new MovieListPresenter(this));

		ButterKnife.bind(this, binding.getRoot());
		recyclerView.setHasFixedSize(true);

		//Prepare the layout manager and hand it over to the view
		prepareLayout();

		recyclerView.setLayoutManager(layoutManager);


		// Scroll listener for pagination manager
		recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
			@Override
			protected void loadMoreItems() {
				isLoading = true;
				binding.getPresenter().increaseCurrentPage();
				binding.getPresenter().loadNextPage();
			}

			@Override
			public int getTotalPageCount() {
				Log.i(TAG, "getTotalPageCount: " + totalPages);
				return totalPages;
			}

			@Override
			public boolean isLastPage() {
				Log.i(TAG, "isLastPage: " + isLastPage);
				return isLastPage;
			}

			@Override
			public boolean isLoading() {
				return isLoading;
			}
		});


		binding.getPresenter().loadFirstPage(SortMovieType.SORT_POPULAR_MOVIES);
		return binding.getRoot();
	}



	@Override
	public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

		MenuItem item = menu.findItem(R.id.action_search);
		searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
		MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
		MenuItemCompat.setActionView(item, searchView);

		MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				menu.findItem(R.id.action_sort).setVisible(false);
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				menu.findItem(R.id.action_sort).setVisible(true);
				binding.getPresenter().startPage();
				binding.getPresenter().loadFirstPage(SortMovieType.SORT_RATED_MOVIES);
				return true;
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {

				binding.getPresenter().searchMovies(query);
				return false;
			}
			@Override
			public boolean onQueryTextChange(String query) {
				return true;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_top_rated:
				binding.getPresenter().startPage();
				binding.getPresenter().loadFirstPage(SortMovieType.SORT_RATED_MOVIES);
				return true;
			case R.id.action_popular:
				binding.getPresenter().startPage();
				binding.getPresenter().loadFirstPage(SortMovieType.SORT_POPULAR_MOVIES);
				return true;
			case R.id.action_upcoming:
				binding.getPresenter().startPage();
				binding.getPresenter().loadFirstPage(SortMovieType.SORT_UPCOMING_MOVIES);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void prepareLayout() {
		layoutManager = new GridLayoutManager(
				getContext(), 1, GridLayoutManager.VERTICAL, false
		);
		recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}


	@Override
	public void onGlobalLayout() {
		recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

		int viewWidth = recyclerView.getMeasuredWidth();
		float posterImageWidth = getResources().getDimension(R.dimen.poster_width);

		// Calculate ratio for poster
		int newSpanCount = (int) Math.floor(viewWidth / posterImageWidth);

		// Set calculated span count on the layout manager
		layoutManager.setSpanCount(newSpanCount);
		layoutManager.requestLayout();
	}

	@Override
	public MovieListViewModel getViewModel() {
		return binding.getViewModel();
	}

	@Override
	public void setViewModel(MovieListViewModel viewModel) {
		binding.setViewModel(viewModel);
	}

	@Override
	public void setAdapter(MovieAdapter adapter) {

		adapter.setContext(getContext());
		adapter.setOnItemClickListener(new ListItemClickListener() {
			@Override
			public void onListItemClick(int clickedItemMovieId) {

				// Build intent to fire detailed pane
				Intent detailViewRequestIntent = new Intent(getContext(), MovieDetailActivity.class);
				// Pass in the movie ID that was clicked to get the relevant movie details
				detailViewRequestIntent.putExtra(MovieDetailFragment.INTENT_MOVIE_ID, clickedItemMovieId);

				startActivity(detailViewRequestIntent);
			}
		});

		binding.recyclerView.setAdapter(adapter);

	}


	@Override
	public void displayMessageToUser(String message) {

	}

	@Override
	public void setTitle(SortMovieType sortMovieType) {
		switch (sortMovieType){
			case SORT_RATED_MOVIES:
				((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_top_rated));
				break;
			case SORT_POPULAR_MOVIES:
				((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_popular));
				break;
			case SORT_UPCOMING_MOVIES:
				((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_upcoming));
				break;
			default:
				((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_popular));
				break;
		}
	}

	@Override
	public void printLog(String message) {
		printLog(TAG, message);
	}

	@Override
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public void setIsLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	@Override
	public void isLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	@Override
	public void refreshData() {
		binding.getPresenter().refreshCurrentData();
		if (!searchView.isIconified()) {
			searchView.onActionViewCollapsed();
		}
	}
}
