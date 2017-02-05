package com.vmn.bento.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by rgr-myrg on 2/5/17.
 */

public class AppLifecycle implements Application.ActivityLifecycleCallbacks {
	@Override
	public void onActivityCreated(Activity activity, Bundle bundle) {
		EventBus.getDefault().post(new LifeCycleEvent("onCreated"));
	}

	@Override
	public void onActivityStarted(Activity activity) {
		EventBus.getDefault().post(new LifeCycleEvent("onStarted"));
	}

	@Override
	public void onActivityResumed(Activity activity) {
		EventBus.getDefault().post(new LifeCycleEvent("onResume"));
	}

	@Override
	public void onActivityPaused(Activity activity) {
		EventBus.getDefault().post(new LifeCycleEvent("onPaused"));
	}

	@Override
	public void onActivityStopped(Activity activity) {
		EventBus.getDefault().post(new LifeCycleEvent("onStopped"));
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
		EventBus.getDefault().post(new LifeCycleEvent("onSaveInstanceState"));
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		EventBus.getDefault().post(new LifeCycleEvent("onDestroyed"));
	}

	public class LifeCycleEvent {
		private String name;

		public LifeCycleEvent(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
