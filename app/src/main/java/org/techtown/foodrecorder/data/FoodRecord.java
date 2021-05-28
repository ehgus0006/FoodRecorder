package org.techtown.foodrecorder.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class FoodRecord {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "food")
    private String food;
    private String time;

    public FoodRecord(String food, String time) {
        this.food = food;
        this.time = time;
    }

    public FoodRecord(){}

    public FoodRecord(int id, String food, String time) {
        this.id = id;
        this.food = food;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
