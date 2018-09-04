package com.saravana.weathermappoc;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    // private AuthCallback authCallback;
    /**
     * Log or request TAG
     */
    public static final String TAG = "Volley_Default_Tag";

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    //public static Context context;
    /**
     * A singleton instance of the application class for easy access in other
     * places
     */
    private static AppController sInstance;

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized AppController getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {

        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);

    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);

    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
