package com.saravana.weathermappoc.util;

import com.saravana.weathermappoc.util.network.FetchRequest;

public interface IController extends IObjectStore
{
    void OnNewData(FetchRequest Fr_in, boolean updateCache_in);
}
