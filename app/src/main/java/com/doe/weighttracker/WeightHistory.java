/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import static java.lang.Math.pow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class WeightHistory extends RecyclerView.Adapter<WeightHistory.WeightViewHolder> {
    private static final int MAX_DAYS = 365;
    private String fileName;
    private static double height;
    private ArrayList<WeightData> history;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy",Locale.US);

    public WeightHistory(Context context) {
        fileName = context.getFilesDir() + "/myWHistory";
        history = readFromFile();
    }

    private ArrayList<WeightData> readFromFile() {
        ArrayList<WeightData> historyFromFile = new ArrayList<>(MAX_DAYS);
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner != null && scanner.hasNext()) {
            int index = 0;
            Date date = null;
            String line = scanner.nextLine();
            String[] data = line.split(",");
            try {
                date = dateFormat.parse(data[index++]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            WeightHistory.height = Double.parseDouble(data[index++]);
            double weight = Double.parseDouble(data[index++]);
            double bmi = Double.parseDouble(data[index++]);
            BMIStatus status = BMIStatus.valueOf(data[index]);
            WeightData read = new WeightData(date, weight, bmi, status);
            historyFromFile.add(read);
        }
        return historyFromFile;
    }

    public void initHistoryFile(double weight, double height) {
        WeightHistory.height = height;
        addToHistoryFile(weight,true);
    }

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem_layout, parent, false);
        return new WeightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        StringBuilder line = new StringBuilder(new String(history.get(position).toString()));
        String[] data = line.toString().split(",");
        line = new StringBuilder();
        data[0] += ", ";
        data[1] = data[2] + " LBS, ";
        data[2] = "BMI: " + data[3]+", ";
        data[3] = data[4] + " WEIGHT";
        data[4] = "";
        for (String temp : data) line.append(temp);
        holder.weightHistoryEntryText.setText(line.toString());
        if (data[3].startsWith("U")) holder.itemView.setBackground(new ColorDrawable(Color.BLUE));
        else if (data[3].startsWith("H")) holder.itemView.setBackground(new ColorDrawable(Color.GREEN));
        else if (data[3].startsWith("O") && data[3].length()<12) holder.itemView.setBackground(new ColorDrawable(Color.YELLOW));
        else holder.itemView.setBackground(new ColorDrawable(Color.RED));
        /*holder.parent.setOnClickListener(v->{
            TextView entry = v.findViewById(R.id.weightHistoryEntryTextView);
        });*/
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public void addToHistoryFile(double weight,boolean initializeHistory) {
        WeightData weightData = new WeightData(weight);
        try {
            Files.write(Paths.get(fileName), (weightData.toString()+'\n').getBytes(StandardCharsets.UTF_8), (initializeHistory ? StandardOpenOption.CREATE : StandardOpenOption.APPEND));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getHeight() {
        return WeightHistory.height;
    }

    private class WeightData {
        private final double weight;
        private final Date date;
        private double bmi = 0;
        private BMIStatus BMIStatus;

        private WeightData(Date date, double weight, double bmi, WeightHistory.BMIStatus BMIStatus) {
            this.weight = weight;
            this.date = date;
            this.bmi = bmi;
            this.BMIStatus = BMIStatus;
        }

        public WeightData(double weight) {
            this.weight = weight;
            this.date = new Date();
            if (getHeight() != 0) {
                bmi = ((weight / pow(height, 2)) * 703);
                bmi = Math.round(100 * bmi);
                bmi /= 100;
                setBMIStatus();
            }
        }

        private void setBMIStatus() {
            if (bmi <= 18.5) BMIStatus = WeightHistory.BMIStatus.UNDER;
            else if (bmi < 25) BMIStatus = WeightHistory.BMIStatus.HEALTHY;
            else if (bmi < 30) BMIStatus = WeightHistory.BMIStatus.OVER;
            else BMIStatus = WeightHistory.BMIStatus.OBESE;
        }

        @NonNull
        @Override
        public String toString() {
            return dateFormat.format(date) + ","+height+',' + weight + ',' + bmi + ',' + BMIStatus;
        }
    }

    private enum BMIStatus {UNDER, HEALTHY, OVER, OBESE}

    static class WeightViewHolder extends RecyclerView.ViewHolder {
        private final TextView weightHistoryEntryText;

        public WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            weightHistoryEntryText = itemView.findViewById(R.id.weightHistoryEntryTextView);
        }
    }

}
