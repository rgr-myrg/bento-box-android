package com.vmn.bento;

import android.app.Application;

import com.vmn.bento.aria.AriaApi;
import com.vmn.bento.aria.AriaClient;
import com.vmn.bento.event.Events;
import com.vmn.bento.model.AriaConfig;
import com.vmn.bento.subscriber.ComscoreSubscriber;
import com.vmn.bento.lifecycle.AppLifecycle;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rgr-myrg on 2/5/17.
 */

public class BentoBox {
	public static final String version = "v1.1";

	private String mAppId;
	private String mData;
	private Events.OnError mOnError;
	private Events.OnConfigReady mOnConfigReady;

	private Error mError = new Error();

	private AriaApi mAriaApi;
	private AppLifecycle mAppLifecycle = new AppLifecycle();

	public static final BentoBox Builder() {
		return new BentoBox();
	}

	public BentoBox registerApplication(Application app) {
		if (app != null) {
			app.registerActivityLifecycleCallbacks(mAppLifecycle);
		}

		return this;
	}

	public BentoBox withAppId(String appId) {
		mAppId = appId;
		return this;
	}

	public BentoBox trackOnCreate(String viewName) {
		mData = viewName;
		return this;
	}

	public BentoBox trackEvent(String eventName) {
		EventBus.getDefault().post(new TrackEvent(eventName));
		return this;
	}

	public BentoBox onError(Events.OnError onError) {
		mOnError = onError;
		return this;
	}

	public BentoBox onConfigReady(Events.OnConfigReady onConfigReady) {
		mOnConfigReady = onConfigReady;
		return this;
	}

	public BentoBox build() {
		// Add a real end-point in AriaClient.BASE_URL to test config requests.
		//requestAppConfig();

		// Pretend we are registering a subscriber enabled in the config
		new ComscoreSubscriber();

		// Demo config ready and error events.
		// Mock callback triggers
		mOnConfigReady.callback(new AriaConfig());
		sendError(Error.CONFIG_ERROR, "Operation not supported");

		return this;
	}

	/*
	 * Invokes AriaClient and enqueues the response.
	 * Triggers callbacks when the response is ready.
	 */
	private void requestAppConfig() {
		if (mAppId == null) {
			sendError(Error.CONFIG_ERROR, "App ID is Null");
		}

		mAriaApi = AriaClient.getClient().create(AriaApi.class);

		final Call<AriaConfig> configCall = mAriaApi.requestAppConfig(mAppId, version);

		configCall.enqueue(new Callback<AriaConfig>() {
			@Override
			public void onResponse(Call<AriaConfig> call, Response<AriaConfig> response) {
				if (response == null || response.body() == null) {
					sendError(Error.CONFIG_ERROR, "Config is Null");
					return;
				}

				if (mOnConfigReady != null) {
					mOnConfigReady.callback(response.body());
				}
			}

			@Override
			public void onFailure(Call<AriaConfig> call, Throwable t) {
				sendError(Error.CONFIG_ERROR, "Unable to load config");
			}
		});
	}

	private void sendError(int type, String message) {
		if (mOnError == null) {
			return;
		}

		mError.setErrorType(type);
		mError.setMessage(message);

		EventBus.getDefault().post(mError);
		mOnError.callback(mError);
	}

	public class TrackEvent {
		private String name;

		public TrackEvent(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public class Error {
		public static final int CONFIG_ERROR = 100;

		private int errorType;
		private String message;

		public void setErrorType(int errorType) {
			this.errorType = errorType;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getErrorType() {
			return errorType;
		}

		public String getMessage() {
			return message;
		}

		public Error(int errorType, String message) {
			this.errorType = errorType;
			this.message = message;
		}

		public Error() {}
	}
}
