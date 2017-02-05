package com.vmn.bento.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.vmn.bento.BentoBox;
import com.vmn.bento.R;

public class BentoActivity extends AppCompatActivity {
	private BentoBox mBentoBox;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		buildBentoBox();
	}

	private void buildBentoBox() {
		mBentoBox = BentoBox.Builder()
				.registerApplication(getApplication())
				.withAppId("coolapp.cc")
				.trackOnCreate("My Activity Data")
				.onError(error -> {
					Log.i("MAIN", error.getMessage());
				})
				.onConfigReady(config -> {
					Log.i("MAIN", "config is ready");
				})
				.build();

		// Mock track on click for demo
		((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(view -> {
			mBentoBox.trackEvent("My Event Data");
		});
	}
}
