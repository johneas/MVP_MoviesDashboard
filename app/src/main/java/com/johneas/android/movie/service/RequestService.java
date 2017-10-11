package com.johneas.android.movie.service;

import com.johneas.android.movie.data.model.MainResponse;
import com.johneas.android.movie.data.model.MovieDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("SameParameterValue")
public interface RequestService {

	//Api documentation
	//https://developers.themoviedb.org/3/movies

	String BASE_URL = "https://api.themoviedb.org/3/";

	@GET("discover/movie")
	Call<MainResponse> getMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber,
			@Query("region") String region
	);

	@GET("movie/top_rated")
	Call<MainResponse> getTopRatedMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber
	);

	@GET("movie/popular")
	Call<MainResponse> getPopularMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber
	);

	@GET("movie/upcoming")
	Call<MainResponse> getUpComingMovies(
			@Query("api_key") String apiKey,
			@Query("page") int pageNumber
	);


	@GET("movie/{movie_id}")
	Call<MovieDetailResponse> getMovie (
			@Path("movie_id") int movieId,
			@Query("api_key") String apiKey
	);

	@GET("search/movie")
	Call<MainResponse> searchMovie (
			@Query("api_key") String apiKey,
			@Query("query") String keyWord,
			@Query("page") int pageNumber

	);
}
