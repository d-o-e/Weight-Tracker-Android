package com.doe.weighttracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewer extends AppCompatActivity {
    private WeightHistory history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        setTitle("Hold down to delete");
        RecyclerView historyRecycleView = findViewById(R.id.history_recycle_view);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        history = new WeightHistory(getApplicationContext());
        historyRecycleView.setHasFixedSize(true);
        historyRecycleView.addItemDecoration(new DividerItemDecoration(historyRecycleView.getContext(), DividerItemDecoration.VERTICAL));
        historyRecycleView.setAdapter(history);
    }
}
