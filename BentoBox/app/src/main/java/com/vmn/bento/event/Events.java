package com.vmn.bento.event;

import com.vmn.bento.BentoBox;
import com.vmn.bento.model.AriaConfig;

/**
 * Created by rgr-myrg on 2/5/17.
 */

public class Events {
	public interface OnError {
		void callback(BentoBox.Error error);
	}
	public interface OnConfigReady {
		void callback(AriaConfig config);
	}
}
