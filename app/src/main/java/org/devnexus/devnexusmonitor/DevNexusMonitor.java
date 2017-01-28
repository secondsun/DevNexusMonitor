package org.devnexus.devnexusmonitor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.WindowManager;

import org.devnexus.devnexusmonitor.display.ScheduleDisplayFragment;
import org.devnexus.devnexusmonitor.display.SetUpFragment;
import org.devnexus.devnexusmonitor.service.SessionStateService;
import org.devnexus.devnexusmonitor.vo.Room;

import java.util.Calendar;


public class DevNexusMonitor extends Activity {


    PowerManager.WakeLock wl;


    BroadcastReceiver sessionStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()) {
                case SessionStateService.EVENT_CHANGE_STATE: {
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction tx = manager.beginTransaction();

                    SessionStateService.DisplayState state = (SessionStateService.DisplayState) intent.getSerializableExtra(SessionStateService.EXTRA_CURRENT_STATE);

                    switch (state) {
                        case LOADING:
                            break;
                        case SETUP:
                            tx.replace(R.id.fragment, SetUpFragment.newInstance());
                            break;
                        case SCHEDULE:
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                            calendar.set(Calendar.DAY_OF_MONTH, 23);
                            calendar.set(Calendar.YEAR, 2017);

                            Room room = new Room();
                            room.id = 115;
                            room.name = "JVM Languages";

                            tx.replace(R.id.fragment, ScheduleDisplayFragment.newInstance(room, "#a6c4e7", calendar.getTime()), "TAG");
                            break;
                        default:
                            throw new IllegalStateException(state + " is not supported");
                    }


                    tx.commit();
                }
                break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "My Tag");
        wl.acquire();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wl.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(sessionStateReceiver, new IntentFilter(SessionStateService.EVENT_CHANGE_STATE));
        startService(new Intent(this, SessionStateService.class).setAction(SessionStateService.ACTION_EMIT_STATE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(sessionStateReceiver);
    }
}
