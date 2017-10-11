package com.johneas.android.movie.service;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.johneas.android.movie.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

	private static Retrofit retrofit = null;

	public static Retrofit getClient() {

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

		if (BuildConfig.DEBUG) {
			okHttpClient.networkInterceptors().add(new StethoInterceptor());
		}

		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(RequestService.BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit;
	}

}
