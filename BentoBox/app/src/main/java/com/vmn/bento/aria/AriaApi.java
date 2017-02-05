package com.vmn.bento.aria;

import com.vmn.bento.model.AriaConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rgr-myrg on 2/5/17.
 */

public interface AriaApi {
	@GET("1.1/aria/bento.js")
	Call<AriaConfig> requestAppConfig(
			@Query("appId") String appId,
			@Query("v") String version
	);
}
