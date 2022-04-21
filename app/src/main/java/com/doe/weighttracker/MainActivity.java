/*
 * @author Deniz Erisgen ©
 */
package com.doe.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editWeight;
    protected EditText editHeight;
    private WeightHistory history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        TextView welcome = findViewById(R.id.welcomeText);
        editHeight = findViewById(R.id.enterHeightTextView);
        editWeight = findViewById(R.id.enterWeightTextView);
        TextView heightText = findViewById(R.id.mainHeightTextView);
        Button addData = findViewById(R.id.addDataButtonView);
        Button showHistory = findViewById(R.id.showHistoryButtonView);

        history = new WeightHistory(this);
        if (history.getHeight() == 0) {
            heightText.setEnabled(false);
            heightText.setVisibility(View.GONE);
            editHeight.setEnabled(true);
            editHeight.setVisibility(View.VISIBLE);
        } else {
            welcome.setText(R.string.mainWelcomeText2);
            heightText.setEnabled(true);
            heightText.setVisibility(View.VISIBLE);
            String heightStr = heightText.getText().toString() +
                    history.getHeight() + " in.";
            heightText.setText(heightStr);
            editHeight.setEnabled(false);
            editHeight.setVisibility(View.GONE);

        }

        addData.setOnClickListener(view -> {
            double weight = Double.parseDouble(editWeight.getText().toString());
            if (Double.isNaN(weight) || weight < 0) return;

            if (editHeight.isEnabled()) {
                double height = Double.parseDouble(
                        editHeight.getText().toString());
                if (history.addToHistoryFile(weight, height))
                    Toast.makeText(this, "History Created"
                            , Toast.LENGTH_SHORT).show();

            } else if (history.addToHistoryFile(weight))
                Toast.makeText(this, weight + " added"
                        , Toast.LENGTH_SHORT).show();
            showHistory();
        });
        showHistory.setOnClickListener(view -> showHistory());

    }

    /**
     * Shows Video Player Activity
     */
    private void showHistory() {
        Intent intent = new Intent(this, HistoryViewer.class);
        startActivity(intent);
    }

}