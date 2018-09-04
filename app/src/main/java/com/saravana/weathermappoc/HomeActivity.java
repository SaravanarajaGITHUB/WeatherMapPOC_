package com.saravana.weathermappoc;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.saravana.weathermappoc.pojo.DailyForecasts;
import com.saravana.weathermappoc.pojo.Weather;
import com.saravana.weathermappoc.pojo.WeatherInfo;
import com.saravana.weathermappoc.util.AppConstants;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.AppHelper;
import com.saravana.weathermappoc.util.ObjectStore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private HomeController homeController;
    private TextView viewTextWeatherLocationOf;
    private TextView viewTextDay;
    private TextView viewTextDayWeatherInfo;
    private ImageView viewImageDayWeatherIcon;
    private TextView viewTextDayTempCandF;
    private CardView viewCardToday, viewCardToday_1, viewCardToday_2, viewCardToday_3;
    private Dialog mDialogueProgress;
    private Button viewButtonChennai, viewButtonMumbai, viewButtonBangalore, viewButtonNewDelhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_home);
            homeController = (HomeController) ObjectStore.I().getObject(AppConstants.OBJ_HOME_CONTROLLER);
            homeController.mHomeActivity = this;
            init();
            homeController.startFRGetDailyForecast("Chennai");
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }

    //set up: for  activity
    private void init() {

        viewTextWeatherLocationOf = findViewById(R.id.viewTextWeatherLocationOf);
        viewTextDay = findViewById(R.id.viewTextDay);
        viewTextDayWeatherInfo = findViewById(R.id.viewTextDayWeatherInfo);
        viewImageDayWeatherIcon = findViewById(R.id.viewImageDayWeatherIcon);
        viewTextDayTempCandF = findViewById(R.id.viewTextDayTempCandF);
        viewCardToday = findViewById(R.id.viewCardToday);

        viewCardToday_1 = findViewById(R.id.viewCardToday_1);
        viewCardToday_2 = findViewById(R.id.viewCardToday_2);
        viewCardToday_3 = findViewById(R.id.viewCardToday_3);

        viewButtonChennai = findViewById(R.id.viewButtonChennai);
        viewButtonMumbai = findViewById(R.id.viewButtonMumbai);
        viewButtonBangalore = findViewById(R.id.viewButtonBangalore);
        viewButtonNewDelhi = findViewById(R.id.viewButtonNewDelhi);

        setListeners();
    }

    //set up view listeners
    private void setListeners() {
        try {

            viewButtonChennai = findViewById(R.id.viewButtonChennai);
            viewButtonMumbai = findViewById(R.id.viewButtonMumbai);
            viewButtonBangalore = findViewById(R.id.viewButtonBangalore);
            viewButtonNewDelhi = findViewById(R.id.viewButtonNewDelhi);

            viewButtonChennai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        homeController.startFRGetDailyForecast(getString(R.string.city_chennai));

                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });
            viewButtonMumbai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        homeController.startFRGetDailyForecast(getString(R.string.city_mumbai));
                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });
            viewButtonBangalore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        homeController.startFRGetDailyForecast(getString(R.string.city_bangalore));
                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });
            viewButtonNewDelhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        homeController.startFRGetDailyForecast(getString(R.string.city_newdelhi));
                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });

            viewCardToday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        HomeModel homeModel = (HomeModel) ObjectStore.I().Get(AppConstants.OBJ_HOME_MODEL, true);
                        if (null != homeModel.dailyForecasts) {
                            showToastOfTodayWeatherDetails(homeModel.dailyForecasts.getList().get(0));
                        } else {
                            Toast.makeText(HomeActivity.this, "No Data to show", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });


        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    //update view
    protected void updateView(DailyForecasts dailyForecasts) {
        try {
            viewTextWeatherLocationOf.setText(dailyForecasts.getCity().getName().concat(", India"));
            Weather weather = dailyForecasts.getList().get(0).getWeather().get(0);
            viewTextDayWeatherInfo.setText(weather.getDescription());
            viewTextDay.setText(getDayFromUTCTimeStamp(dailyForecasts.getList().get(0).getDt()));

            //set weather icon
            if (null != weather.getIcon()) {
                Glide.with(this).load(AppHelper.URL_GET_WEATHER_ICON.toString().concat(weather.getIcon()).concat(".png"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).dontAnimate().dontTransform())
                        .into(viewImageDayWeatherIcon);
            }
            viewTextDayTempCandF.setText("" + dailyForecasts.getList().get(0).getTemp().getDay() + " K");

            //update sub weather item views
            updateSubViewData(viewCardToday_1, dailyForecasts.getList().get(1));
            updateSubViewData(viewCardToday_2, dailyForecasts.getList().get(2));
            updateSubViewData(viewCardToday_3, dailyForecasts.getList().get(3));
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    //update view helper class
    private void updateSubViewData(CardView viewCardToday_in, final WeatherInfo weatherInfo) {
        try {

            TextView viewTextDay;
            ImageView viewImageDayWeatherIcon;
            TextView viewTextDayTempCandF;
            viewImageDayWeatherIcon = viewCardToday_in.findViewById(R.id.viewImageDayWeatherIcon);
            viewTextDay = viewCardToday_in.findViewById(R.id.viewTextDay);
            viewTextDayTempCandF = viewCardToday_in.findViewById(R.id.viewTextDayTempCandF);
            viewTextDay.setText(getDayFromUTCTimeStamp(weatherInfo.getDt()));
            //set weather icon
            if (null != weatherInfo.getWeather().get(0).getIcon()) {
                Glide.with(this).load(AppHelper.URL_GET_WEATHER_ICON.toString().concat(weatherInfo.getWeather().get(0).getIcon()).concat(".png"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).dontAnimate().dontTransform())
                        .into(viewImageDayWeatherIcon);
            }
            viewTextDayTempCandF.setText("" + weatherInfo.getTemp().getDay() + " K");
            //more detail on click of card view
            viewCardToday_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        showToastOfTodayWeatherDetails(weatherInfo);
                    } catch (Exception e) {
                        AppDataLog.TPostExep(e);
                    }
                }
            });
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    private void showToastOfTodayWeatherDetails(final WeatherInfo weatherInfo_in) {
        try {
            String stTodayWeatherDetails =
                    "Humidity : " + weatherInfo_in.getHumidity() + "\n"
                            + "Pressure : " + weatherInfo_in.getPressure() + "\n"
                            + "Wind speed : " + weatherInfo_in.getSpeed();

            Toast.makeText(this, stTodayWeatherDetails, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    //convert UTC timestamp to get current day
    private String getDayFromUTCTimeStamp(String stDate_in) {
        try {
            long timeStamp = Long.parseLong(stDate_in) * 1000L;
            SimpleDateFormat sdfDay = new SimpleDateFormat("E");
            SimpleDateFormat sdfDateTime = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
            Date netDate = (new Date(timeStamp));
            return sdfDay.format(netDate);
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
            return null;
        }
    }

    //basic loading animation
    public void openDialogueLoadingAnimation(final boolean bStatus, final String stLoadingMessage_in) {
        try {
            if (!isFinishing()) { // to check view is alive to do changes to avoid exception
                if (!bStatus) {
                    if (null != mDialogueProgress && mDialogueProgress.isShowing()) {
                        mDialogueProgress.dismiss();
                    }
                } else {
                    if (null != mDialogueProgress && mDialogueProgress.isShowing()) {
                        mDialogueProgress.dismiss();
                        mDialogueProgress = null;
                    }
                    AlertDialog.Builder builderProgress = new AlertDialog.Builder(this);
                    View viewContent = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialogue_progress, null, false);
                    TextView viewTextProgressMessage = viewContent.findViewById(R.id.viewTextProgressMessage);
                    viewTextProgressMessage.setText(stLoadingMessage_in);

                    builderProgress.setView(viewContent);
                    mDialogueProgress = builderProgress.create();
                    mDialogueProgress.show();
                }
            }

        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeController.cleanUp();
    }
}
