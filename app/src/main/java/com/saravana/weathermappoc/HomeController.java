package com.saravana.weathermappoc;

import android.app.Activity;
import android.content.Intent;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saravana.weathermappoc.pojo.DailyForecasts;
import com.saravana.weathermappoc.util.AppConstants;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.AppHelper;
import com.saravana.weathermappoc.util.IController;
import com.saravana.weathermappoc.util.ObjectStore;
import com.saravana.weathermappoc.util.network.DataManager;
import com.saravana.weathermappoc.util.network.FetchRequest;
import com.saravana.weathermappoc.util.network.RestSVCMapper;
import com.saravana.weathermappoc.util.network.RestDataManager;
import com.saravana.weathermappoc.util.network.fetchrequest.FRGetDailyForecast;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class HomeController implements IController {

    private final String TAG = getClass().getSimpleName();

    protected HomeActivity mHomeActivity;
    private RestSVCMapper _restSVCMapper;
    private DataManager _restDataMgr;
    HomeModel homeModel;

    HomeController() {
        homeModel = (HomeModel) ObjectStore.I().Get(AppConstants.OBJ_HOME_MODEL, true);

        _restDataMgr = RestDataManager.getInstance();
        _restSVCMapper = RestSVCMapper.getInstance();
    }


    public void startFRGetDailyForecast(String stCityName) {
        try {
            if (null != mHomeActivity) {
                mHomeActivity.openDialogueLoadingAnimation(true, "Fetching data .. ");
            }

            FetchRequest fetchReq = new FRGetDailyForecast();

            //set URL
            String stUrl = "";
            stUrl = _restSVCMapper.GetRootURL() + _restSVCMapper.
                    GetServiceURLWithTag(AppHelper.URL_GET_DAILY_FORECAST) + stCityName
                    + AppHelper.URL_GET_DAILY_FORECAST_P2
                    + AppHelper.APP_ID;

            fetchReq.setRequestURL(stUrl);

            //set controller
            fetchReq.setController(this);

            //set requestType
            fetchReq.setRequestType(AppConstants.REQUEST_TYPE_JSON_OBJECT);

            //set requestParams
            fetchReq.setRequestParameter(null);

            String stResKey = FRGetDailyForecast.class.getSimpleName();

            //set resourceKey
            fetchReq.setResourceKey(stResKey);

            //Set requestTag
            fetchReq.setRequestTag(TAG);

            //set volley priority
            fetchReq.setPriority(Request.Priority.IMMEDIATE);

            //set RESTVerb
            fetchReq.setRESTVerb(AppConstants.REST_GET);

            //add fetch request to the queue
            _restDataMgr.addFetchRequestToQueue(fetchReq);

        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }

    @Override
    public void OnNewData(final FetchRequest Fr_in, boolean updateCache_in) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    if (Fr_in instanceof FRGetDailyForecast) {

                        mHomeActivity.openDialogueLoadingAnimation(false, null);
                        if (!Fr_in._bIsError) {
                            JSONObject response = (JSONObject) Fr_in._responseData;
                            AppDataLog.TLog(AppConstants.TAG_FLOW_CHK + " FRGetDailyForecast ",
                                    "Response " + response.toString(), AppDataLog.nLOG_i);
                            onReceivedFRGetDailyForecast(response);
                        } else {
                            AppDataLog.TLog(AppConstants.TAG_FLOW_CHK + " FRGetDailyForecast",
                                    "Failed", AppDataLog.nLOG_w);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.run();
    }

    private void onReceivedFRGetDailyForecast(JSONObject response) {
        try {
            Type listType = new TypeToken<DailyForecasts>() {
            }.getType();
            DailyForecasts dailyForecasts = new Gson().fromJson(response.toString(), listType);

            homeModel.setData(dailyForecasts);
            mHomeActivity.updateView(homeModel.dailyForecasts);

        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    @Override
    public void cleanUp() {
        try {
            //flush all our data
            homeModel.cleanUp();
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }

    public void startHomeActivity(Activity splashScreenActivity) {
        try {
            Intent intent = new Intent(splashScreenActivity, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("IsObjectStoreCleared", false);
            AppController.getAppContext().startActivity(intent);
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }
}
