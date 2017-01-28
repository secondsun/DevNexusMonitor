package org.devnexus.devnexusmonitor.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import org.devnexus.devnexusmonitor.ApplicationController;
import org.devnexus.devnexusmonitor.R;

/**
 * An IntentService which will emit state changes as necessary
 */
public class SessionStateService extends IntentService {

    public enum DisplayState {LOADING, SETUP, SCHEDULE};

    /**
     * This event will emit with a payload that will instruct the {@link org.devnexus.devnexusmonitor.DevNexusMonitor} to update its child views.
     */
    public static final String EVENT_CHANGE_STATE = "org.devnexus.devnexusmonitor.event.CHANGE_STATE";

    /**
     * This event will emit with a payload that will instruct the {@link org.devnexus.devnexusmonitor.DevNexusMonitor} to update its child views.
     */
    public static final String EXTRA_CURRENT_STATE = "org.devnexus.devnexusmonitor.event.CURRENT_STATE";


    /**
     * Sending this action to this service will make it emit the current state.
     */
    public static final String ACTION_EMIT_STATE = "org.devnexus.devnexusmonitor.event.action.EMIT_STATE";



    public SessionStateService() {
        super("SessionStateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ApplicationController controller = ApplicationController.getInstance(this);

        switch (intent.getAction()) {
            case ACTION_EMIT_STATE: {

                Intent response = new Intent(EVENT_CHANGE_STATE);

                if (!controller.isSetup()) {
                    response.putExtra(EXTRA_CURRENT_STATE, DisplayState.SETUP);
                }

                LocalBroadcastManager.getInstance(this).sendBroadcast(response);
            }
        }
    }
}
