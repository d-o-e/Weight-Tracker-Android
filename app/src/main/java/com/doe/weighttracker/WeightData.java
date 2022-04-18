package com.doe.weighttracker;

import static java.lang.Math.pow;
import java.util.Date;

public class WeightData {
    private double weight;
    private double height;
    private Date date;
    private double bmi;
    private Weight_Status wStatus;

    public WeightData(double weight, Date date) {
        this.weight = weight;
        this.date = date;
        bmi = (weight /pow(height,2) ) * 703;
        setBMIStatus();
    }

    private void setBMIStatus(){
        if (bmi<=18.5) wStatus = Weight_Status.UNDER;
        else if (bmi<25) wStatus =  Weight_Status.HEALTHY;
        else if (bmi<30) wStatus =  Weight_Status.OVER;
        else wStatus = Weight_Status.OBESE;
    }

    enum Weight_Status {UNDER,HEALTHY,OVER,OBESE}
}
