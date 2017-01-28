package org.devnexus.devnexusmonitor.service;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Handles InstanceID events
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
