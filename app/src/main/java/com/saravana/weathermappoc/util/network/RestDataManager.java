package com.saravana.weathermappoc.util.network;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.saravana.weathermappoc.AppController;
import com.saravana.weathermappoc.util.AppConstants;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.IController;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RestDataManager extends DataManager {

    private static RestDataManager ourInstance = new RestDataManager();

    public static RestDataManager getInstance() {
        return ourInstance;
    }

    private static Map<String, FetchRequest> _hmRequestKeyWithFetchReq = new ConcurrentHashMap<>();
    private FetchRequest _fetchReqInRDM;
    private ArrayList<IController> _lstControllerInRDM;
    private RetryPolicy retryPolicy = new DefaultRetryPolicy(
            AppConstants.VOLLEY_TIMEOUT_MS,
            AppConstants.VOLLEY_MAX_RETRIES,
            AppConstants.VOLLEY_BACKOFF_MULT
    );

    private RestDataManager() {

    }

    @Override
    public void addFetchRequestToQueue(FetchRequest fetchRequest_in) {

        try {
            if (_hmRequestKeyWithFetchReq.containsKey(fetchRequest_in._stResourceKey)) {

                //Get Existing FetchRequest in RDM of the same ResourceKey
                _fetchReqInRDM = _hmRequestKeyWithFetchReq.get(fetchRequest_in._stResourceKey);

                //Get Existing List of Controller in RDM from Existing FetchRequest in RDM
                _lstControllerInRDM = _fetchReqInRDM._lstIController;

                // Get New List of Controller from new incoming fetchRequest
                ArrayList<IController> lstController = fetchRequest_in._lstIController;

                if (null != lstController) {
                    // Get the first Controller alone from the new incoming FetchRequest
                    IController controller = lstController.get(0);

                    //Checking Condition for Duplicate Request From Same Controller
                    boolean bIsDuplicateFromDifferentController = true;
                    for (IController controllerInRDM : _lstControllerInRDM) {
                        if (controllerInRDM.getClass().getSimpleName().equals(controller.getClass().getSimpleName())) {
                            bIsDuplicateFromDifferentController = false;
                            break;
                        }
                    }
                    if (bIsDuplicateFromDifferentController) {
                        _lstControllerInRDM.add(controller);
                        AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_DUPLICATION, fetchRequest_in._stResourceKey + ": Duplicate from Different Controller", AppDataLog.nLOG_i);
                    } else {
                        AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_DUPLICATION, fetchRequest_in._stResourceKey + ": Duplicate from Same Controller", AppDataLog.nLOG_i);
                    }
                }
            } else {
                _hmRequestKeyWithFetchReq.put(fetchRequest_in._stResourceKey, fetchRequest_in);
                AppDataLog.TLongLog(AppConstants.TAG_FLOW_CHK, fetchRequest_in._stResourceKey, AppDataLog.nLOG_d);
                this.execFetchRequest(fetchRequest_in);
            }
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }

    @Override
    public void execFetchRequest(final FetchRequest fetchRequest_in) {

        switch (fetchRequest_in._nRequestType) {
            case AppConstants.REQUEST_TYPE_JSON_OBJECT: {
                AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_URL, fetchRequest_in._stResourceKey + " URL: " + fetchRequest_in._stServiceURL, AppDataLog.nLOG_i);
                AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_PARAMS, fetchRequest_in._stResourceKey + " Params: " + fetchRequest_in._objPost, AppDataLog.nLOG_i);
                this.processJSONObject(fetchRequest_in);
            }
            break;
            case AppConstants.REQUEST_TYPE_STRING: {
                AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_URL, fetchRequest_in._stResourceKey + " URL: " + fetchRequest_in._stServiceURL, AppDataLog.nLOG_i);
                //this.jsonStringRequest(fetchRequest_in);
            }
            break;
            case AppConstants.REQUEST_TYPE_IMAGE: {
                AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_URL, fetchRequest_in._stResourceKey + " URL: " + fetchRequest_in._stServiceURL, AppDataLog.nLOG_i);
                //this.jsonImageRequest(fetchRequest_in);
            }
            break;

            default:
                break;
        }
    }


    private void processJSONObject(FetchRequest fetchRequest_in) {
        try {
            if (fetchRequest_in._stRESTVerb.equals(AppConstants.REST_POST)) {
                //create POST JSONRequest object

            } else if (fetchRequest_in._stRESTVerb.equals(AppConstants.REST_GET)) {
                if (fetchRequest_in._getHeaderParams == null) {
                    //create GET JSONRequest object
                    this.jsonGETRequest(fetchRequest_in);

                } else {
                    //create GET JSONRequest object with header params

                }
            }
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }

    private void jsonGETRequest(final FetchRequest fetchRequest_in) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                fetchRequest_in._stServiceURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            AppDataLog.TLongLog(AppConstants.TAG_RDM_SUCCESS, fetchRequest_in._stResourceKey,AppDataLog.nLOG_i);
                            AppDataLog.TLongLog(AppConstants.TAG_RDM_REQ_RESPONSE,
                                    fetchRequest_in._stResourceKey + "=" + response.toString(),AppDataLog.nLOG_i);
                            fetchRequest_in._responseData = response;
                            _fetchReqInRDM = _hmRequestKeyWithFetchReq.get(fetchRequest_in._stResourceKey);

                            //Get Existing List of Controller in RDM from Existing FetchRequest in RDM
                            _lstControllerInRDM = _fetchReqInRDM._lstIController;

                            int nUpdateCacheCounter = 0;

                            // Notifying Response to all the Controller which contains the same key
                            for (IController iController : _lstControllerInRDM) {
                                if (null != iController) {
                                    if (nUpdateCacheCounter == 0) {
                                        iController.OnNewData(fetchRequest_in, true);
                                    } else {
                                        iController.OnNewData(fetchRequest_in, false);
                                    }
                                    nUpdateCacheCounter++;
                                }
                            }
                            // Removing the key
                            _hmRequestKeyWithFetchReq.remove(fetchRequest_in._stResourceKey);

                        } catch (Exception e) {
                            AppDataLog.TPostExep(e);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error instanceof TimeoutError
                            || error instanceof NoConnectionError) {
                        Toast.makeText(AppController.getInstance(), "No Network Connection", Toast.LENGTH_LONG).show();
                        AppDataLog.TLongLog(AppConstants.TAG_RDM + "_ErrorType=",
                                "TimerOutError or NoConnectionError",AppDataLog.nLOG_i);
                    } else if (error instanceof AuthFailureError) {
                        AppDataLog.TLongLog(AppConstants.TAG_RDM + "_ErrorType=", "AuthFailureError",AppDataLog.nLOG_i);

                    } else if (error instanceof ServerError) {
                        AppDataLog.TLongLog(AppConstants.TAG_RDM + "_ErrorType=", "ServerError",AppDataLog.nLOG_i);
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(AppController.getInstance(), "No Network Connection", Toast.LENGTH_LONG).show();
                        AppDataLog.TLongLog(AppConstants.TAG_RDM + "_ErrorType=", "NetworkError",AppDataLog.nLOG_i);
                    } else if (error instanceof ParseError) {
                        AppDataLog.TLongLog(AppConstants.TAG_RDM + "_ErrorType=", "ParseError",AppDataLog.nLOG_i);
                    }

                    AppDataLog.TLongLog(AppConstants.TAG_RDM_FAILURE, fetchRequest_in._stResourceKey,AppDataLog.nLOG_i);
                    //Setting the Error
                    fetchRequest_in._responseError = error;
                    fetchRequest_in._bIsError = true;

                    //Fetch FetchReq from RDM
                    _fetchReqInRDM = _hmRequestKeyWithFetchReq.get(fetchRequest_in._stResourceKey);

                    //Get Existing List of Controller in RDM from Existing FetchRequest in RDM
                    _lstControllerInRDM = _fetchReqInRDM._lstIController;

                    // Notifying Response to all the Controller which contains the same key
                    for (IController iController : _lstControllerInRDM) {
                        if (null != iController) {
                            iController.OnNewData(fetchRequest_in, false);
                        }
                    }
                    // Removing the key
                    _hmRequestKeyWithFetchReq.remove(fetchRequest_in._stResourceKey);
                } catch (Exception e) {
                    AppDataLog.TPostExep(e);
                }
            }
        }) {
            @Override
            public Priority getPriority() {
                AppDataLog.TLongLog(AppConstants.TAG_RDM_PRIORITY_CHK,
                        fetchRequest_in._stResourceKey + " = " + fetchRequest_in._priority,AppDataLog.nLOG_i);
                return fetchRequest_in._priority;
            }
        };
        //Adding Volley RetryPolicy
        jsonObjReq.setRetryPolicy(retryPolicy);

        AppController.getInstance().addToRequestQueue(jsonObjReq, fetchRequest_in._stRequestTag);
    }
}
