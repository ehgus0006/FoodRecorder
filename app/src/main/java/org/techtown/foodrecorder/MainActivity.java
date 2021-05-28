package org.techtown.foodrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.techtown.foodrecorder.data.FoodRecord;
import org.techtown.foodrecorder.data.FoodRecordDatabase;
import org.techtown.foodrecorder.data.FoodRecordOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    private FoodRecordOpenHelper helper;
    private SharedPreferences preferences;
    private FoodRecordDatabase db;
    TextView textView, textView2;
    EditText editText;
    private View.OnClickListener onSave = v->{
        SharedPreferences.Editor editor = preferences.edit();
        String food = editText.getText().toString();
        if (!food.isEmpty()) {
            editor.putString("food", food);
            String now = LocalDateTime.now().toString();
            editor.putString("time", now);
            editor.apply();
//            helper.addRecord(new FoodRecord(food, now));
            save(new FoodRecord(food, now));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        helper = new FoodRecordOpenHelper(this, "db", null, 1);
//        ArrayList<FoodRecord> list = helper.getRecords();
//        for (FoodRecord r : list) {
//            Log.i("Main", r.getFood() + r.getTime());
//        }

        db = FoodRecordDatabase.getInstance(getApplicationContext());
        getList();

        textView = findViewById(R.id.textViewRecord);
        textView2 = findViewById(R.id.textViewElapsed);
        editText = findViewById(R.id.editText);

        preferences = getSharedPreferences("food", Context.MODE_PRIVATE);
        String lastFood = preferences.getString("food", null);
        String lastTime = preferences.getString("time", null);
        displayRecord(lastFood, lastTime);
        Button button = findViewById(R.id.buttonRecord);
        button.setOnClickListener(onSave);

        Button button1 = findViewById(R.id.buttonShowAll);
        button1.setOnClickListener(v->{
            startActivity(new Intent(this, RecordActivity.class));

        });
    }

    private void displayRecord(String lastFood, String savedTime) {
        if (lastFood == null) {
            textView.setText("저장된 기록이 없습니다.");
            textView2.setText("경과 시간이 없습니다.");
        }else{
            LocalDateTime startTime = LocalDateTime.parse(savedTime);
            LocalDateTime endTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm", Locale.KOREA);

            String timeStr = startTime.format(formatter);
            String foodMessage = String.format("%s - %s", timeStr, lastFood);
            textView.setText(foodMessage);

            long hours = ChronoUnit.HOURS.between(startTime, endTime);
            long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
            minutes -= hours * 60;
            textView2.setText(String.format(Locale.KOREA, "%d시간 %02d분 경과", hours, minutes));

        }
    }

    private void save(FoodRecord record) {
        new Thread(() -> db.foodRecordDAO().addRecord(record)).start();
    }
    private void getList() {
        new Thread(() -> {
            List<FoodRecord> result = db.foodRecordDAO().getRecords();
            for(FoodRecord e:result)
                Log.i("Main", e.getTime() + e.getFood());
        }).start();
    }


}