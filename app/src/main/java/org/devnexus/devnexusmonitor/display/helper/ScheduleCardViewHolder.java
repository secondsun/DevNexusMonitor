package org.devnexus.devnexusmonitor.display.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import org.devnexus.devnexusmonitor.R;
import org.devnexus.devnexusmonitor.vo.ScheduleItem;
import org.devnexus.devnexusmonitor.vo.Speaker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by summers on 1/1/17.
 */

public class ScheduleCardViewHolder extends RecyclerView.ViewHolder {

    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

    private ScheduleItem item;

    private View itemView;
    private final TextView startTime;
    private final TextView sessionTitle;
    private final TextView[] speakers = new TextView[4];

    public ScheduleCardViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        startTime = (TextView) itemView.findViewById(R.id.start_time) ;
        sessionTitle = (TextView) itemView.findViewById(R.id.session_title);
        speakers[0] = (TextView) itemView.findViewById(R.id.speaker_name_1);
        speakers[1] = (TextView) itemView.findViewById(R.id.speaker_name_2);
        speakers[2] = (TextView) itemView.findViewById(R.id.speaker_name_3);
        speakers[3] = (TextView) itemView.findViewById(R.id.speaker_name_4);
    }

    public ScheduleItem getItem() {
        return item;
    }

    public void setItem(ScheduleItem item) {
        this.item = item;
        startTime.setText(TIME_FORMAT.format(item.fromTime));
        itemView.setBackgroundColor(Color.parseColor(item.room.color));
        for (TextView speaker : speakers) {
            speaker.setText("");
        }

        if (item.presentation != null) {
            sessionTitle.setText(item.presentation.title);
            itemView.setBackgroundColor(Color.parseColor(item.presentation.track.color));
            for (int i = 0; i < item.presentation.speakers.size(); i++) {
                if (i > 3) {
                    break;
                }

                Speaker speaker = item.presentation.speakers.get(i);

                speakers[i].setText(String.format("%s %s", speaker.firstName, speaker.lastName));
            }

        } else if (item.title != null ) {
            sessionTitle.setText(item.title);
        } else {
            sessionTitle.setText("TBD");
        }



    }



}
