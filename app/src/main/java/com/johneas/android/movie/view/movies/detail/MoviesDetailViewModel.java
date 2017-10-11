package com.johneas.android.movie.view.movies.detail;

import android.databinding.ObservableField;

import com.johneas.android.movie.view.common.base.BaseViewModel;


public class MoviesDetailViewModel extends BaseViewModel{

    public final ObservableField<Integer> ratingValue = new ObservableField<>(0);
    public final ObservableField<String> ratingQuantity = new ObservableField<>("");
    public final ObservableField<String> releaseDate = new ObservableField<>("");
    public final ObservableField<String> synopsis = new ObservableField<>("");
    public final ObservableField<String> posterUrl = new ObservableField<>("");

}
