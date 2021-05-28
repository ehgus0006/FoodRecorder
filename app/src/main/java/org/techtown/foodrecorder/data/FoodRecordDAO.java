package org.techtown.foodrecorder.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;

import java.util.List;

@Dao
public interface FoodRecordDAO {

    @Insert
    Long addRecord(FoodRecord record);

    @Query("select * from food")
    @TypeConverter
    List<FoodRecord> getRecords();

    @Query("select * from food where id=:id")
    FoodRecord get(int id);

    @Delete
    int delete(FoodRecord record);
}
