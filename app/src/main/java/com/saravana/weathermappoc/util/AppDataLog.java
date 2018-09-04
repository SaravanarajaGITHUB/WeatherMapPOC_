package com.saravana.weathermappoc.util;

import android.util.Log;

public class AppDataLog {

    public static final int nLOG_e = 0;
    public static final int nLOG_w = 1;
    public static final int nLOG_i = 2;
    public static final int nLOG_d = 3;
    public static final int nLOG_v = 4;

    /*Log.e:
    This is for when bad stuff happens.
    Use this tag in places like inside a catch statment.
    You know that an error has occurred and therefore you're logging an error.

    Log.w:
    Use this when you suspect something shady is going on.
    You may not be completely in full on error mode, but maybe you recovered from some unexpected behavior.
    Basically, use this to log stuff you didn't expect to happen but isn't necessarily an error.
    Kind of like a "hey, this happened, and it's weird, we should look into it."

    Log.i:
    Use this to post useful information to the log.
    For example: that you have successfully connected to a server.
    Basically use it to report successes.

    Log.d:
    Use this for debugging purposes.
    If you want to print out a bunch of messages so you can log the exact flow of your program,
    use this. If you want to keep a log of variable values, use this.

    Log.v:
    Use this when you want to go absolutely nuts with your logging.
    If for some reason you've decided to log every little thing in a particular part of your app, use the Log.v tag.
    And as a bonus...

    Log.wtf:
    Use this when stuff goes absolutely, horribly, holy-crap wrong.
    You know those catch blocks where you're catching errors that you never should get...yea, if you wanna log them use Log.wtf*/

    public static boolean _bDataLog = true;

    /*
     * Logging  the Message in Log
     * @params String stMessage_in = Message to be shown in Log
     * @params String stTag_in = Tag to be shown in Log
     * */
    public static void TLog(String stTag_in, String stMessage_in, int nLogType_in) {
        if (_bDataLog) {

            switch (nLogType_in) {

                case nLOG_e:
                    Log.e(stTag_in, stMessage_in);
                    break;

                case nLOG_w:
                    Log.w(stTag_in, stMessage_in);
                    break;

                case nLOG_i:
                    Log.i(stTag_in, stMessage_in);
                    break;

                case nLOG_d:
                    Log.d(stTag_in, stMessage_in);
                    break;

                case nLOG_v:
                    Log.v(stTag_in, stMessage_in);
                    break;
            }

        }
    }


    /*
     * Logging  the Message in Log
     * @params String stMessage_in = Message to be shown for Common Log
     * */
    public static void TLog(String stMessage_in) {
        if (_bDataLog) {
            Log.i("Common Log", stMessage_in);
        }
    }


    /*
     * Post the Exception Information in Log
     * @params Exception excep_in = Exception Message to be display in Log
     * */
    public static void TPostExep(Exception excep_in) {
        if (_bDataLog) {
            if (null != excep_in) {
                AppDataLog.TLog("Exception : ", Log.getStackTraceString(excep_in), nLOG_e);
            }
        }
    }

    public static void TPostExep(Exception excep_in, String stClientLogType) {
        //Android studio Exception
        if (_bDataLog) {
            if (null != excep_in) {
                AppDataLog.TLog("Exception : ", Log.getStackTraceString(excep_in), nLOG_e);
            }
        }

    }

    /*
     * Post the Exception Information in Log
     * @params Exception className_in = Exception from which class
     * @params Exception excep_in = Exception Message to be display in Log
     * */
    public static void TPostExep(String className_in, Exception excep_in) {
        if (_bDataLog) {
            if (null != excep_in) {
                AppDataLog.TLog("" + className_in, Log.getStackTraceString(excep_in), nLOG_e);
            }
        }
    }

    /*
     * Post the Exception Message in Log
     * @params String stMessage_in = Message to be display in Log
     * */
    public static void PostExcepCase(String stMessage_in) {
        if (_bDataLog) {
            AppDataLog.TLog("exception", stMessage_in, nLOG_e);
        }
    }

    public static void TLongLog(String stTag_in, String stMessage_in, int nLogType_in) {

        if (_bDataLog) {

            switch (nLogType_in) {

                case nLOG_e:
                    if (stMessage_in.length() > 4000) {
                        Log.e(stTag_in, stMessage_in.substring(0, 4000));
                        TLongLog(stTag_in, stMessage_in.substring(4000), nLogType_in);
                    } else {
                        Log.e(stTag_in, stMessage_in);
                    }
                    break;

                case nLOG_w:
                    if (stMessage_in.length() > 4000) {
                        Log.w(stTag_in, stMessage_in.substring(0, 4000));
                        TLongLog(stTag_in, stMessage_in.substring(4000), nLogType_in);
                    } else {
                        Log.w(stTag_in, stMessage_in);
                    }
                    break;

                case nLOG_i:
                    if (stMessage_in.length() > 4000) {
                        Log.i(stTag_in, stMessage_in.substring(0, 4000));
                        TLongLog(stTag_in, stMessage_in.substring(4000), nLogType_in);
                    } else {
                        Log.i(stTag_in, stMessage_in);
                    }
                    break;

                case nLOG_d:
                    if (stMessage_in.length() > 4000) {
                        Log.d(stTag_in, stMessage_in.substring(0, 4000));
                        TLongLog(stTag_in, stMessage_in.substring(4000), nLogType_in);
                    } else {
                        Log.d(stTag_in, stMessage_in);
                    }
                    break;

                case nLOG_v:
                    if (stMessage_in.length() > 4000) {
                        Log.v(stTag_in, stMessage_in.substring(0, 4000));
                        TLongLog(stTag_in, stMessage_in.substring(4000), nLogType_in);
                    } else {
                        Log.v(stTag_in, stMessage_in);
                    }
                    break;
            }

        }

    }

}