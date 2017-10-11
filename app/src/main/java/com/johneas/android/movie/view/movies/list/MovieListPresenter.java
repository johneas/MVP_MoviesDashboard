package com.johneas.android.movie.view.movies.list;

import android.support.annotation.NonNull;

import com.johneas.android.movie.data.model.MainResponse;
import com.johneas.android.movie.data.model.Movie;
import com.johneas.android.movie.service.RequestService;
import com.johneas.android.movie.service.RetrofitFactory;
import com.johneas.android.movie.view.common.base.BasePresenter;
import com.johneas.android.movie.view.common.utils.Constants;
import com.johneas.android.movie.view.movies.list.adapter.MovieAdapter;
import com.johneas.android.movie.view.movies.list.enums.SortMovieType;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter extends BasePresenter
        implements MovieListContract.presenter {

    private MovieListContract.view view;
    private CompositeDisposable compositeDisposable;
    private RequestService apiConnection;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    private SortMovieType currentSortMovieType;
    private MovieAdapter adapter;
    private int totalPages;
    private String searchWord;

    public MovieListPresenter(MoviesListFragment moviesListFragment) {

        this.view = moviesListFragment;

        compositeDisposable = new CompositeDisposable();
        apiConnection = RetrofitFactory.getClient().create(RequestService.class);

        adapter = new MovieAdapter();
        view.setAdapter(adapter);

    }


    @Override
    public void increaseCurrentPage() {
        currentPage++;
    }

    @Override
    public void startPage() {
        currentPage = PAGE_START;
    }

    @Override
    public void refreshCurrentData() {
        loadFirstPage(currentSortMovieType);
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public void onDataRefresh() {
        view.refreshData();
    }

    private Call<MainResponse> callMoviesApi(SortMovieType sortMovieType) {

        view.setTitle(sortMovieType);

        switch (sortMovieType){
            case SORT_RATED_MOVIES:
                return apiConnection.getTopRatedMovies(Constants.MOVIE_API_KEY, currentPage);
            case SORT_POPULAR_MOVIES:
                return apiConnection.getPopularMovies(Constants.MOVIE_API_KEY, currentPage);
            case SORT_UPCOMING_MOVIES:
                return apiConnection.getUpComingMovies(Constants.MOVIE_API_KEY, currentPage);
            case SORT_SEARCH_MOVIES:
                return apiConnection.searchMovie(Constants.MOVIE_API_KEY, searchWord, currentPage);
            default:
                return apiConnection.getMovies(Constants.MOVIE_API_KEY, currentPage, "US");
        }

    }

    public void loadFirstPage(SortMovieType sortMovieType) {

        currentSortMovieType = sortMovieType;
        adapter.clear();
        view.getViewModel().isLoading.set(true);

        callMoviesApi(sortMovieType).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<MainResponse> call,
                    @NonNull Response<MainResponse> response
            ) {
                List<Movie> movies = fetchMovies(response);
                adapter.addAll(movies);

                view.getViewModel().isLoading.set(false);
                if (currentPage == totalPages) {
                    view.isLastPage(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                view.printLog(t.toString());
                view.getViewModel().isLoading.set(false);
            }
        });
    }


    /**
     * Make a call for next page
     */
    public void loadNextPage() {

        callMoviesApi(currentSortMovieType).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<MainResponse> call,
                    @NonNull Response<MainResponse> response
            ) {
                view.setIsLoading(false);
                List<Movie> movies = fetchMovies(response);
                adapter.addAll(movies);

                if (currentPage == totalPages) {
                    view.isLastPage(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                view.printLog(t.getMessage());
            }
        });
    }

    /**
     * Make a search for movie
     *
     * @param wordToSearch word to search
     */
    public void searchMovies(String wordToSearch){

        searchWord = wordToSearch;
        adapter.clear();

        callMoviesApi(SortMovieType.SORT_SEARCH_MOVIES).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<MainResponse> call,
                    @NonNull Response<MainResponse> response
            ) {
                List<Movie> movies = fetchMovies(response);
                adapter.addAll(movies);
                view.getViewModel().isLoading.set(false);
                if (currentPage == totalPages) {
                    view.isLastPage(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                view.printLog(t.toString());
            }
        });
    }

    /**
     * Parse data response
     *
     * @param response api response
     * @return movie list
     */
    private List<Movie> fetchMovies(Response<MainResponse> response) {
        MainResponse rawResponse = response.body();
        assert rawResponse != null;
        totalPages = rawResponse.getTotalPages();
        return rawResponse.getMovies();
    }

}
