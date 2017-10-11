package com.johneas.android.movie.view.movies.list;

import com.johneas.android.movie.view.common.interfaces.ViewModelView;
import com.johneas.android.movie.view.movies.list.adapter.MovieAdapter;
import com.johneas.android.movie.view.movies.list.enums.SortMovieType;

public class MovieListContract {

    public interface view extends ViewModelView<MovieListViewModel> {
        void setAdapter(MovieAdapter adapter);
        void displayMessageToUser(String message);
        void setTitle(SortMovieType sortMovieType);
        void printLog(String message);
        void setTotalPages(int totalPages);
        void setIsLoading(boolean isLoading);
        void isLastPage(boolean isLastPage);
        void refreshData();
    }

    public interface presenter{
        void increaseCurrentPage();
        void startPage();
        void refreshCurrentData();
        int  getCurrentPage();
        void onDataRefresh();
    }
}
