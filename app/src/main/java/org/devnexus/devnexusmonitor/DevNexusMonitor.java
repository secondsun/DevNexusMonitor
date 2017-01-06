package org.devnexus.devnexusmonitor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.devnexus.devnexusmonitor.display.ScheduleDisplayFragment;
import org.devnexus.devnexusmonitor.vo.Room;

import java.util.Calendar;

import static java.util.Calendar.MONTH;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DevNexusMonitor extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();

        Calendar calendar = Calendar.getInstance();
        calendar.set(MONTH, Calendar.FEBRUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        calendar.set(Calendar.YEAR, 2017);



        Room room = new Room();
        room.id = 115;
        room.name = "JVM Languages";

        tx.add(R.id.fragment, ScheduleDisplayFragment.newInstance(room, "#a6c4e7", calendar.getTime()), "TAG");
        tx.commit();
    }
}
