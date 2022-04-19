package com.doe.weighttracker;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewer extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        RecyclerView historyRecycleView = findViewById(R.id.history_recycle_view);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        WeightHistory history = new WeightHistory(getApplicationContext());
        historyRecycleView.setHasFixedSize(true);
        historyRecycleView.addItemDecoration(new DividerItemDecoration(historyRecycleView.getContext(), DividerItemDecoration.VERTICAL));
        historyRecycleView.setAdapter(history);
    }
}
