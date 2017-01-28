package org.devnexus.devnexusmonitor.display;


import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.devnexus.devnexusmonitor.R;
import org.devnexus.devnexusmonitor.display.helper.RoomScheduleViewAdapter;
import org.devnexus.devnexusmonitor.util.GsonUtils;
import org.devnexus.devnexusmonitor.vo.Room;
import org.devnexus.devnexusmonitor.vo.Schedule;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * How to configure :
 * Calendar calendar = Calendar.getInstance();
 * calendar.set(MONTH, Calendar.FEBRUARY);
 * calendar.set(Calendar.DAY_OF_MONTH, 23);
 * calendar.set(Calendar.YEAR, 2017);
 *
 * Room room = new Room();
 * room.id = 115;
 * room.name = "JVM Languages";
 *
 * tx.add(R.id.fragment, ScheduleDisplayFragment.newInstance(room, "#a6c4e7", calendar.getTime()), "TAG");
 * tx.commit();
 *
 */
public class ScheduleDisplayFragment extends Fragment {

    private static final String ROOM_NAME_ARG = "ROOM_NAME";
    private static final String ROOM_ID_ARG = "ROOM_ID";
    private static final String THEME_COLOR_ARG = "THEME_COLOR_ARG";
    private static final String DATE_ARG = "DATE_ARG";
    private String title = "Java";
    private int color = Color.CYAN;
    private Date date = new Date();
    private int roomId = 0;

    public static ScheduleDisplayFragment newInstance(Room room, String themeColor, Date date) {
        ScheduleDisplayFragment fragment = new ScheduleDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ROOM_NAME_ARG, room.name);
        args.putInt(ROOM_ID_ARG, room.id);
        args.putString(THEME_COLOR_ARG, themeColor);
        args.putLong(DATE_ARG, date.getTime());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.title = getArguments().getString(ROOM_NAME_ARG);
            this.color = Color.parseColor(getArguments().getString(THEME_COLOR_ARG));
            this.roomId = (getArguments().getInt(ROOM_ID_ARG));
            this.date = new Date(getArguments().getLong(DATE_ARG));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dev_nexus_monitor, null);

        scheduleRecyclerGrid = (RecyclerView) view.findViewById(R.id.schedule_grid);

        GridLayoutManager lm = new GridLayoutManager(getActivity(), 4);
        scheduleRecyclerGrid.setLayoutManager(lm);
        scheduleRecyclerGrid.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 15;
                outRect.top = 15;
                outRect.left = 5;
                outRect.right = 5;
            }
        });
        scheduleRecyclerGrid.setAdapter(new RoomScheduleViewAdapter());

        ((TextView)view.findViewById(R.id.screen_title)).setText(title);
        ((TextView)view.findViewById(R.id.screen_title)).setTextColor(color);

        return view;
    }

    private RecyclerView scheduleRecyclerGrid;




    @Override
    public void onStart() {
        super.onStart();

        final AsyncTask<Void, Void, Schedule> scheduleLoader = new AsyncTask<Void, Void, Schedule>() {
            @Override
            protected Schedule doInBackground(Void... params) {
                try {
                    return GsonUtils.GSON.fromJson(new InputStreamReader(getActivity().getAssets().open("schedule.json")), Schedule.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Schedule schedule) {
                super.onPostExecute(schedule);
                RoomScheduleViewAdapter adapter = new RoomScheduleViewAdapter();

                Room room = new Room();
                room.id = roomId;
                room.color = String.format("#%06X", (0xFFFFFF & color));
                adapter.configure(schedule, date, room);
                scheduleRecyclerGrid.setAdapter(adapter);
            }
        };

        scheduleLoader.execute();
    }


}
