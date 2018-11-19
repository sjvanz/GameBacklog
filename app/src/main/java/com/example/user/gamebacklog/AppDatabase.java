package com.example.user.gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;


@Database(entities = {Backlog.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract BacklogDao backlogGameDao();

    private final static String NAME_DATABASE = "backlog_db";

    //Static instance
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .build();
        }
        return sInstance;
    }
}

