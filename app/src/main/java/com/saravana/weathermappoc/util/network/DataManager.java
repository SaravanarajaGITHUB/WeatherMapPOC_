package com.saravana.weathermappoc.util.network;


import com.saravana.weathermappoc.util.IController;

public abstract class DataManager {

    public abstract void addFetchRequestToQueue(FetchRequest fetchRequest_in);

    public abstract void execFetchRequest(FetchRequest fetchRequest_in);

//    public abstract void Cancel(IController controller_in);
//
//    public abstract void CancelWithTags(String requestTags_in);
//
//    public abstract void CancelWithKey(String resourceKey_in);


}
