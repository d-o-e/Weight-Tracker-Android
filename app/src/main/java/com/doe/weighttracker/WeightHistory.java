/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import static java.lang.Math.pow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;

public class WeightHistory extends RecyclerView.Adapter<WeightHistory.WViewHolder> implements Runnable{
    private static final int MAX_DAYS = 365;
    private ArrayList<WeightData> history = new ArrayList<>(MAX_DAYS);
    private Context parent;
    private String fileName;

    public WeightHistory(Context context) {
        parent = context;
        fileName = parent.getFilesDir()+"/myWHistory";
        run();
    }

    @Override
    public void run() {
        history = readFromFile(fileName);
        if (history == null) createHistoryFile(fileName);

    }

    private ArrayList<WeightData> readFromFile(String fileName) {
        ArrayList<WeightData> readFromFile = new ArrayList<>(MAX_DAYS);
        FileInputStream fin = null;
        int index = 0;
        try {
            fin = new FileInputStream(fileName);
            // read bytes to history array
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return readFromFile;
    }

    public void save() {
        // TODO: 4/18/2022 auto save on destroy or while suspend
        /*byte[] bytes = 
        Files.write((parent.getFilesDir()+fileName), , )*/
    }

    @NonNull
    @Override
    public WViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem_layout, parent,false);
        return new WViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WViewHolder holder, int position) {
        holder.weightHistoryEntryText.setText(history.get(position).toString());
        /*holder.parent.setOnClickListener(v->{
            TextView entry = v.findViewById(R.id.weightHistoryEntryTextView);
        });*/
    }

    @Override
    public int getItemCount() {
        return history.size();
    }



    private void createHistoryFile(String fileName) {
        try {
            Files.write(Paths.get(fileName), new WeightData(0,new Date()).toString().getBytes(StandardCharsets.UTF_8),StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class WViewHolder extends RecyclerView.ViewHolder {
        private TextView weightHistoryEntryText;
        private LinearLayout parent;

        public WViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.linearHistoryLayout);
            weightHistoryEntryText = itemView.findViewById(R.id.weightHistoryEntryTextView);
        }
    }

    private class WeightData {
        private float weight;
        private float height;
        private Date date;
        private float bmi;
        private BMIStatus BMIStatus;

        public WeightData(float weight) {
            this.weight = weight;
            // TODO: 4/18/2022 init with current time
            this.date = new Date();
            bmi = (float) ((weight /pow(height,2) ) * 703.0);
            setBMIStatus();
        }

        // TODO: 4/18/2022 maybe add a private const. for reading from file

        private void setBMIStatus(){
            if (bmi<=18.5) BMIStatus = WeightHistory.BMIStatus.UNDER;
            else if (bmi<25) BMIStatus = WeightHistory.BMIStatus.HEALTHY;
            else if (bmi<30) BMIStatus = WeightHistory.BMIStatus.OVER;
            else BMIStatus = WeightHistory.BMIStatus.OBESE;
        }

        @NonNull
        @Override
        public String toString() {
            return date + "," + weight + " lbs" + ", bmi=" + bmi +", " + BMIStatus;
        }
    }
    private enum BMIStatus {UNDER,HEALTHY,OVER,OBESE}
}
