package com.saravana.weathermappoc.util.network;

import android.content.Context;

import com.saravana.weathermappoc.util.AppConstants;
import com.saravana.weathermappoc.util.AppHelper;

import java.util.HashMap;

public class RestSVCMapper {
    //public static RestSVCMapper _SharedSingleton = null;
    public HashMap<String, String> restMapperDictionary = new HashMap<String, String>();
    //SharedPreferenceHelper _sharedPref;
    public Context context;

    private static RestSVCMapper ourInstance = new RestSVCMapper();

    public static RestSVCMapper getInstance() {
        return ourInstance;
    }

    protected RestSVCMapper() {
        setDictionary(new AppHelper().parse());

    }

    /*
     * Saving the RootURL and APIName in the HashMap
     * @params HashMap<String, String> restMapperDictionary = RootURL and ApiName in HM
     * */
    public void setDictionary(HashMap<String, String> restMapperDictionary_in) {
        this.restMapperDictionary = restMapperDictionary_in;
    }

    /*
     * Get the service url for the given tag
     * @params String stKey_in = Key value of the specific Api
     * @return stServiceURL_ret = Return the Api Name for the respective key
     * */
    public String GetServiceURLWithTag(String stKey_in) {
        String stServiceURL_ret = null;
        stServiceURL_ret = restMapperDictionary.get(stKey_in);
        return stServiceURL_ret;
    }

    /*
     * Get the RootURL of the Service
     * @return String = Returning the root url
     * */
    public String GetRootURL() {

        return GetServiceURLWithTag(AppHelper.ROOT_URL);
    }


}
