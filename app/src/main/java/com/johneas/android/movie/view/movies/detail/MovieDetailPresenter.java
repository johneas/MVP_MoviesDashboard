package com.johneas.android.movie.view.movies.detail;

import android.support.annotation.NonNull;

import com.johneas.android.movie.data.model.MovieDetailResponse;
import com.johneas.android.movie.service.RequestService;
import com.johneas.android.movie.service.RetrofitFactory;
import com.johneas.android.movie.view.common.base.BasePresenter;
import com.johneas.android.movie.view.common.utils.Constants;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter implements MovieDetailContract.presenter {

    private MovieDetailContract.view view;
    private RequestService apiConnection;

    public MovieDetailPresenter(MovieDetailContract.view view) {
        this.view = view;
        apiConnection = RetrofitFactory.getClient().create(RequestService.class);
    }

    @Override
    public void loadMovieDetails(int idMovie) {
        callMoviesApi(idMovie).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailResponse> call,
                                   @NonNull Response<MovieDetailResponse> response) {
                MovieDetailResponse movie = response.body();

                view.getViewModel().posterUrl.set(Constants.POSTER_IMAGE_BASE_URL +
                        Constants.POSTER_IMAGE_SIZE + movie.getBackdropPath());

                BigDecimal getVoteAverage =  new BigDecimal(movie.getVoteAverage());
                float tenPointRating = getVoteAverage.floatValue();
                float convertedRating = 5 * (tenPointRating / 10);
                view.getViewModel().ratingValue.set((int) convertedRating);
                view.getViewModel().ratingQuantity.set(String.format("(%s)", movie.getVoteCount()));
                view.getViewModel().releaseDate.set(movie.getReleaseDate());
                view.getViewModel().synopsis.set(movie.getOverview());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private Call<MovieDetailResponse> callMoviesApi(int idMovie) {
        if (idMovie != 0) {
            return apiConnection.getMovie(idMovie, Constants.MOVIE_API_KEY);
        }
        view.printLog("Movie ID: " + idMovie);
        return null;
    }
}
