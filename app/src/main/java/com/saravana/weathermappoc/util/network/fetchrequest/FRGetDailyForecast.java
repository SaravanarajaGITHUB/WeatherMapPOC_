package com.saravana.weathermappoc.util.network.fetchrequest;

import com.android.volley.Request;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.IController;
import com.saravana.weathermappoc.util.network.FetchRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SR on 22/03/17.
 */

public class FRGetDailyForecast extends FetchRequest {

    public FRGetDailyForecast(){
        set_nAPI_VersionNumber(1);
    }

    @Override
    public void setRequestParameter(Object objPost_in) {

        try {
            this._objPost = (JSONObject) objPost_in;
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    @Override
    public void setController(IController controller_in) {
        this._lstIController = new ArrayList<IController>();
        this._lstIController.add(controller_in);
    }

    @Override
    public void setRequestURL(String stServiceURL_in) {
        this._stServiceURL = stServiceURL_in;
    }

    @Override
    public void setRequestType(int nRequestType_in) {
        _nRequestType = nRequestType_in;
    }

    @Override
    public void setHeaderParams(String contentType_in, String api_Key_in) {

    }

    @Override
    public void setResourceKey(String stResoruceKey_in) {
        this._stResourceKey = stResoruceKey_in;
    }

    @Override
    public void setRESTVerb(String stRESTVerb_in) {
        this._stRESTVerb = stRESTVerb_in;
    }

    @Override
    public void setRequestTag(String stRequestTag_in) {
        this._stRequestTag = stRequestTag_in;

    }

    @Override
    public void setPriority(Request.Priority priority_in) {
        this._priority = priority_in;
    }

    @Override
    public void set_nAPI_VersionNumber(int nAPI_versionNumber_in) {
        this._nAPI_VersionNumber = nAPI_versionNumber_in;
    }

    @Override
    public void setParams(Map<String, String> hmParams) {

    }
}
