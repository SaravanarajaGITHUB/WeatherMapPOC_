package com.saravana.weathermappoc.util.network;

import com.android.volley.Request.Priority;
import com.android.volley.VolleyError;
import com.saravana.weathermappoc.util.IController;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FetchRequest {
    public String _stServiceURL;
    public JSONObject _objPost;
    public String _stRESTVerb;
    public Object _responseData;
    public int _nRequestType;
    public HashMap<String, String> _getHeaderParams = null;
    public VolleyError _responseError = null;
    public String _stResourceKey;
    public String _stRequestTag;
    public int _nAPI_VersionNumber;

    public ArrayList<IController> _lstIController;
    public Priority _priority = Priority.NORMAL;

    public boolean _bIsError = false;
    public Map<String, String> hmParam;

    public abstract void setRequestParameter(Object objPost_in);

    public abstract void setController(IController controller_in);

    public abstract void setRequestURL(String stServiceURL_in);

    public abstract void setRequestType(int nRequestType_in);

    public abstract void setHeaderParams(String stContentType_in, String stApi_Key_in);

    public abstract void setResourceKey(String stResourceKey_in);

    public abstract void setRESTVerb(String stRESTVerb_in);

    public abstract void setRequestTag(String stRequestTag_in);

    public abstract void setPriority(Priority priority_in);

    public abstract void set_nAPI_VersionNumber(int nAPI_versionNumber_in);

    public abstract void setParams(Map<String, String> hmParams);

}
