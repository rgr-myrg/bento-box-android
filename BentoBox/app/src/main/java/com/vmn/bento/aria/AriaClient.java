package com.vmn.bento.aria;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates Retrofit Client with Retrofit.Builder()
 * Created by rgr-myrg on 2/5/17.
 */

public class AriaClient {
	public static final String BASE_URL = "http://btg.mtvnservices.com/aria/";
	private static Retrofit sRetrofit;

	public static final Retrofit getClient() {
		if (sRetrofit == null) {
			sRetrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}

		return sRetrofit;
	}
}
