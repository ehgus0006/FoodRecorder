package org.techtown.foodrecorder.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FoodRecord.class}, version = 1)
public abstract class FoodRecordDatabase extends RoomDatabase {

    public abstract FoodRecordDAO foodRecordDAO();

    private static FoodRecordDatabase instance;

    public static FoodRecordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    FoodRecordDatabase.class,
                    "db")
                    .build();
        }
        return instance;
    }

}
