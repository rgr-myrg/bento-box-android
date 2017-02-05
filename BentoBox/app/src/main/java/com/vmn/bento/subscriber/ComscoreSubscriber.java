package com.vmn.bento.subscriber;

import android.util.Log;

import com.vmn.bento.BentoBox;
import com.vmn.bento.lifecycle.AppLifecycle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by rgr-myrg on 2/5/17.
 */

public class ComscoreSubscriber {
	public static final String TAG = ComscoreSubscriber.class.getSimpleName();

	public ComscoreSubscriber() {
		Log.i(TAG, "Register with EventBus");
		EventBus.getDefault().register(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onTrackEvent(BentoBox.TrackEvent event) {
		Log.i(TAG, "onTrackEvent: " + event.getName());
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onLifeCycleEvent(AppLifecycle.LifeCycleEvent event) {
		Log.i(TAG, "onLifeCycleEvent: " + event.getName());
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onBentoErrorEvent(BentoBox.Error error) {
		Log.i(TAG, "onBentoErrorEvent: " + error.getMessage());
	}
}
