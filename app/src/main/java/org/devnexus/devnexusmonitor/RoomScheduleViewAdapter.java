package org.devnexus.devnexusmonitor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.devnexus.devnexusmonitor.vo.Room;
import org.devnexus.devnexusmonitor.vo.Schedule;
import org.devnexus.devnexusmonitor.vo.ScheduleItem;
import org.devnexus.devnexusmonitor.vo.Track;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by summers on 1/1/17.
 */

public class RoomScheduleViewAdapter extends RecyclerView.Adapter<ScheduleCardViewHolder> {

    private Schedule schedule;
    private Date displayDate;
    private Room room;

    private List<ScheduleItem> displayItems = new ArrayList<>(8);

    @Override
    public ScheduleCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = View.inflate(parent.getContext(), R.layout.schedule_card, null);
        return new ScheduleCardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ScheduleCardViewHolder holder, int position) {
        ScheduleItem displayItem = displayItems.get(position);
        holder.setItem(displayItem);
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public void configure(Schedule schedule, Date displayDate, Room room) {
        this.schedule = schedule;
        this.displayDate = displayDate;
        this.room = room;
        displayItems.clear();
        displayItems.addAll(findDisplayItem());
    }

    private List<ScheduleItem> findDisplayItem() {

        List<ScheduleItem> items = new ArrayList<>(8);

        for (ScheduleItem item : schedule.scheduleItems) {
            Date fromTime = item.fromTime;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(fromTime);
            cal2.setTime(displayDate);
            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
            if (sameDay && room.id == item.room.id) {
                items.add(item);
            }
        }
        return  items;
    }

}
