package org.techtown.foodrecorder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodRecordOpenHelper extends SQLiteOpenHelper {

    public FoodRecordOpenHelper(@Nullable Context context,
                                @Nullable String name,
                                @Nullable SQLiteDatabase.CursorFactory factory,
                                int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table foods (id integer primary key autoincrement,"+
                "food text, time text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table foods";
        db.execSQL(sql);
        onCreate(db);
    }

    public long addRecord(FoodRecord record) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("food", record.getFood());
        values.put("time", record.getTime());

        return db.insert("foods", null, values);
    }


    public int delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(id)};
        return db.delete("foods", "id=?", args);
    }

    public ArrayList<FoodRecord> getRecords() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("foods",
                null,
                null,
                null,
                null,
                null,
                null);
        ArrayList<FoodRecord> result = new ArrayList<>();
        while (c.moveToNext()) {
            FoodRecord r = new FoodRecord(
                    c.getInt(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("food")),
                    c.getString(c.getColumnIndex("time")));
            result.add(r);
        }
        c.close();
        return result;
    }
}
