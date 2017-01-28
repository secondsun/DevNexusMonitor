package org.devnexus.devnexusmonitor;

import android.content.Context;

/**
 * This class is the "God Class" for the application.
 *
 * It is responsible for performing the actions of the applicaiton like setup, storing values, etc.
 *
 */

public class ApplicationController {

    private static ApplicationController instance;
    private final Context applicationContext;

    private ApplicationController(Context applicationContext) {
        this.applicationContext = applicationContext.getApplicationContext();
    }

    public synchronized static ApplicationController getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationController(context);
        }
        return instance;
    }

    public synchronized boolean isSetup() {
        return false;
    }
}
