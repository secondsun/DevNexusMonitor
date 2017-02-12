package org.devnexus.devnexusmonitor.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;

import org.devnexus.devnexusmonitor.ApplicationController;
import org.devnexus.devnexusmonitor.R;

/**
 * An IntentService which handle processing events emitted from components and requests from components.
 */
public class MediatorService extends IntentService {

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
     *
     * To receive the current state you will need to be subscribed to the EVENT_CHANGE_STATE event
     * and check the EXTRA_CURRENT_STATE extra.
     */
    public static final String ACTION_EMIT_STATE = "org.devnexus.devnexusmonitor.event.action.EMIT_STATE";

    /**
     * Emit the current FCM token.  To receive the token you will need to listen for the EVENT_FCM_TOKEN
     * Intent and check the EXTRA_FCM_TOKEN extra.
     */
    public static final String ACTION_EMIT_FCM_TOKEN = "org.devnexus.devnexusmonitor.event.EMIT_FCM_TOKEN";

    /**
     * Update and emit the current FCM token.  To update the token please include the token in the
     * intent with the EXTRA_FCM_TOKEN extra.  This call will, if successful, emit EVENT_FCM_TOKEN.
     *
     * To receive the token you will need to listen for the EVENT_FCM_TOKEN
     * Intent and check the EXTRA_FCM_TOKEN extra.
     */
    public static final String ACTION_UPDATE_FCM_TOKEN = "org.devnexus.devnexusmonitor.event.UPDATE_FCM_TOKEN";


    /**
     * A Event containing the current FCM token.
     */
    public static final String EVENT_FCM_TOKEN = "org.devnexus.devnexusmonitor.event.FCM_TOKEN";

    /**
     * FCM token extra
     */
    public static final String EXTRA_FCM_TOKEN = "org.devnexus.devnexusmonitor.event.EXTRA_FCM_TOKEN";


    public MediatorService() {
        super("MediatorService");
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
            break;
            case ACTION_UPDATE_FCM_TOKEN:
            case ACTION_EMIT_FCM_TOKEN: {

                Intent response = new Intent(EVENT_FCM_TOKEN);
                response.putExtra(EXTRA_FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());

                LocalBroadcastManager.getInstance(this).sendBroadcast(response);
            }
            break;
        }
    }
}
