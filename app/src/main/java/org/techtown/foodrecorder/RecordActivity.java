package org.techtown.foodrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.techtown.foodrecorder.data.FoodRecord;
import org.techtown.foodrecorder.data.FoodRecordDatabase;
import org.techtown.foodrecorder.data.FoodRecordOpenHelper;
import org.techtown.foodrecorder.data.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity
        implements RecordAdapter.OnItemClickListener{

    private FoodRecordOpenHelper helper;
    private RecordAdapter adapter;

    private FoodRecordDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

//        helper = new FoodRecordOpenHelper(this, "db", null, 1);
        db = FoodRecordDatabase.getInstance(getApplicationContext());

        LinearLayoutManager manager = new LinearLayoutManager(this);
//        adapter = new RecordAdapter(helper.getRecords(),this);
        adapter = new RecordAdapter(null, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        new LoadTask().execute();
    }

    @Override
    public void onItemClick(View v, int position, FoodRecord record) {
        new DeleteTask().execute(record);
//        helper.delete(position);
//        adapter.setData(helper.getRecords());
//        adapter.notifyDataSetChanged();
    }

    class LoadTask extends AsyncTask<Void, Void, List<FoodRecord>>{

        @Override
        protected List<FoodRecord> doInBackground(Void... voids) {
            return db.foodRecordDAO().getRecords();
        }

        @Override
        protected void onPostExecute(List<FoodRecord> foodRecords) {
            super.onPostExecute(foodRecords);
            adapter.setData((ArrayList<FoodRecord>) foodRecords);
            adapter.notifyDataSetChanged();
        }
    }

    class DeleteTask extends AsyncTask<FoodRecord, Void, List<FoodRecord>> {

        @Override
        protected List<FoodRecord> doInBackground(FoodRecord... args) {
            int result = db.foodRecordDAO().delete(args[0]);
            if (result == 1) {
                return db.foodRecordDAO().getRecords();
            }else
                return null;
        }

        @Override
        protected void onPostExecute(List<FoodRecord> foodRecords) {
            super.onPostExecute(foodRecords);
            if (foodRecords != null) {
                adapter.setData((ArrayList<FoodRecord>) foodRecords);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
