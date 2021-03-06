package org.devnexus.devnexusmonitor.util;

import android.content.ContentResolver;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.devnexus.devnexusmonitor.vo.ScheduleItem;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for getting builders and gsons which work with DevNexus
 */
public final class GsonUtils {
    private GsonUtils() {
    }


    private static final GsonBuilder builder;
    public static final Gson GSON;

    static {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());// + 5 * 60 * 60 * 1000);//Json dates are GMT, translate to EST
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());// - 5 * 60 * 60 * 1000);
            }
        });
        GSON = builder.create();
    }


}
