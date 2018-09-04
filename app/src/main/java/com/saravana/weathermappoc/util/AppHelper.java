package com.saravana.weathermappoc.util;

import com.saravana.weathermappoc.AppController;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AppHelper {

    public static final String APP_ID = "b608f3b525a576cbf9c40e144b8a4402";
    public static final String ROOT_URL = "root_url_web_api";
    public static final String URL_GET_DAILY_FORECAST = "url_get_daily_forecast";
    public static final String URL_GET_DAILY_FORECAST_P2 = ",IN&mode=JSON&units=metric&cnt=4&appid=";
    public static final String URL_GET_WEATHER_ICON = "http://openweathermap.org/img/w/";

    public HashMap<String, String> parse() {
        XmlPullParserFactory pullParserFactory;
        HashMap<String, String> property = null;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = AppController.getInstance()
                    .getAssets().open("AppConfig.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            property = parseXML(parser);

        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
        return property;
    }


    private HashMap<String, String> parseXML(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String text = null;
        HashMap<String, String> dictionary = null;
        String key = null;
        String value = null;
        dictionary = new HashMap<String, String>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (key != null && value != null) {
                        dictionary.put(key, value);
                        key = null;
                        value = null;

                    }
                    if (name.equals("value")) {
                        value = text;
                    } else if (name.equals("key")) {
                        key = text;
                    }

            }
            eventType = parser.next();
        }

        return dictionary;
    }

}
