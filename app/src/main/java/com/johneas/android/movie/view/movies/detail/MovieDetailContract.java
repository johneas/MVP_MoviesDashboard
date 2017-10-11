package com.johneas.android.movie.view.movies.detail;

import com.johneas.android.movie.view.common.interfaces.ViewModelView;

public class MovieDetailContract {

    public interface view extends ViewModelView<MoviesDetailViewModel> {
        void printLog(String message);
    }

    public interface presenter{
        void loadMovieDetails(int idMovie);
    }
}
