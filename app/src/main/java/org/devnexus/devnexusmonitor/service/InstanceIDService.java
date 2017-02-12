package org.devnexus.devnexusmonitor.service;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Handles InstanceID events
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        startService(new Intent(this, MediatorService.class)
                            .setAction(MediatorService.ACTION_UPDATE_FCM_TOKEN)
                            .putExtra(MediatorService.EXTRA_FCM_TOKEN,
                                      FirebaseInstanceId.getInstance().getToken())
                    );

    }
}
