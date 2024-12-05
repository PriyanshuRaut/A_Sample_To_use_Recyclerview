package com.masters.recyclerview.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.masters.recyclerview.database.dao.DAO;
import com.masters.recyclerview.database.model.modelDatabase;

@Database(entities = {modelDatabase.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "user")
                            .fallbackToDestructiveMigration() // Add this line to allow destructive migration
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
