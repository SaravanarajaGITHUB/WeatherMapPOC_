package com.saravana.weathermappoc.util;

import com.saravana.weathermappoc.HomeModel;

import java.util.HashMap;

public class ObjectStore {

    private final String TAG = this.getClass().getSimpleName();
    private static volatile ObjectStore instance = new ObjectStore();

    // private constructor
    private ObjectStore() {
    }

    public static ObjectStore I() {
        return instance;
    }

    public HashMap<String, Object> _hashMapStore = new HashMap<String, Object>();

    /*
     * Saving the object of the class in the Object Store
     * @params String stKey_in = Key value of the object to save in Hashmap
     * @params Object objectValue_in = Object of the class
     * */
    public void putObject(String stKey_in, Object objectValue_in) {
        _hashMapStore.put(stKey_in, objectValue_in);
        AppDataLog.TLog(this.TAG, "Object Created :" + stKey_in, AppDataLog.nLOG_i);
    }

    public Object getObject(String stKey_in) {
        if (null != _hashMapStore && _hashMapStore.containsKey(stKey_in)) {
            return _hashMapStore.get(stKey_in);
        }
        return null;
    }

    /*
     * Removing the object from object store
     * @params String stKey_in = To represent the class name or model name or controller name as a key
     * @returns Boolean = status of the Removing the object in ObjectStore
     * */
    public Boolean removeObject(String stKey_in) {
        try {
            if (null != _hashMapStore && _hashMapStore.containsKey(stKey_in)) {
                _hashMapStore.remove(stKey_in);
                AppDataLog.TLog(this.TAG, "Object is removed in ObjectStore:" + stKey_in, AppDataLog.nLOG_i);
                return true;
            } else {
                AppDataLog.TLog(this.TAG, "Object is not Available in ObjectStore:" + stKey_in, AppDataLog.nLOG_i);
            }
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
        return false;
    }

    /*
     * Create the object of the model if it is not already created
     * @params String stKey_in = Represent the Model Name as key
     * @params boolean bCreateIfNotFound_in = Create the new object for the model if it is true Otherwise return the old value
     * @return Object object_ret = Returning the Object of model
     * */
    public Object Get(String stKey_in, boolean bCreateIfNotFound_in) {
        Object object_ret = null;
        if (null != _hashMapStore) {
            object_ret = _hashMapStore.get(stKey_in);
        }
        if (object_ret == null) {
            if (bCreateIfNotFound_in) {
                object_ret = Create(stKey_in);
            }
        }
        return object_ret;
    }

    /*
     * Creating the Object for the Model and stored the objects in the ObjectStore
     * @params String stKey_in =  Get the key name to represent the model name
     * @return Object object_ret = returning the object of the respective model
     * */

    private Object Create(String stKey_in) {
        Object object_ret = null;
        if (_hashMapStore == null) {
            _hashMapStore = new HashMap<>();
        }
        switch (stKey_in) {

            case AppConstants.OBJ_HOME_MODEL:
                HomeModel homeModel = new HomeModel();
                _hashMapStore.put(stKey_in, homeModel);
                object_ret = homeModel;
                AppDataLog.TLog(AppConstants.TAG_CHK_MEMALLOC + "SubBillModel is allocated");
                break;
        }

        return object_ret;
    }

}
