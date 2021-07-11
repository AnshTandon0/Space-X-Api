package com.example.spacexapi;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CrewModal.class,version = 1,exportSchema = false)
public abstract class CrewDatabase extends RoomDatabase {

    public abstract CrewDao crewDao();

    private static CrewDatabase db;

    public static CrewDatabase buildInstance ( Context context )
    {
        if ( db == null )
        {
            return Room.databaseBuilder(context,CrewDatabase.class,"database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
