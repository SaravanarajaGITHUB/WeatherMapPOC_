package com.saravana.weathermappoc;

import com.saravana.weathermappoc.pojo.DailyForecasts;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.IObjectStore;

public class HomeModel implements IObjectStore {

    public DailyForecasts dailyForecasts = new DailyForecasts();

    public void setData(DailyForecasts dailyForecasts_in) {
        try {
            if (null != dailyForecasts_in) {
                this.dailyForecasts = dailyForecasts_in;
            }
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    @Override
    public void cleanUp() {
        try {
            //flush all our data
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }
}
