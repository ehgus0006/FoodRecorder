package org.techtown.foodrecorder.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.foodrecorder.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ItemViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View v, int position, FoodRecord record);
    }

    private OnItemClickListener listener;

    private ArrayList<FoodRecord> recordData;

    public void setData(ArrayList<FoodRecord> data) {
        this.recordData = data;
    }

    public RecordAdapter(ArrayList<FoodRecord> recordData, OnItemClickListener listener){
        this.recordData = recordData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ItemViewHolder holder, int position) {
        FoodRecord record = recordData.get(position);
        String str = String.format("%d) %s - %s", record.getId(), record.getTime(), record.getFood());
        holder.textView.setText(str);
        holder.itemView.setOnClickListener(v->{
            listener.onItemClick(v, position, record);
        });
    }

    @Override
    public int getItemCount() {
        return (recordData == null) ? 0 : recordData.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View itemView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.textViewContent);
        }
    }

}
