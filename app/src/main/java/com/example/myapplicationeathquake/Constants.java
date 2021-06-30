package com.example.myapplicationeathquake;

import java.util.Random;

public class Constants {
    public static final String BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson";
    public static final int LIMIT = 30;

    public static int randomInt(int max, int min){
        return new Random().nextInt(max - min) + min;
    }
}
