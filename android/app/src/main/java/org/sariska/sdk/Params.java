package org.sariska.sdk;

import android.os.Bundle;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

class Params {
    public static WritableMap createParams(String action) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        return params;
    }

    public static WritableMap createParams(String action, String param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        return params;
    }

    public static WritableMap createParams(String action, Boolean param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putBoolean("param1", param1);
        return params;
    }

    public static WritableMap createParams(String action, int param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        return params;
    }

    public static WritableMap createParams(String action, String param1, String param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        params.putString("param2", param2);
        return params;
    }

    public static WritableMap createParams(String action, int param1, int param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putInt("param2", param2);
        return params;
    }

    public static WritableMap createParams(String action, int param1, Boolean param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putBoolean("param2", param2);
        return params;
    }

    public static WritableMap createParams(String action, int param1, String param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putString("param2", param2);
        return params;
    }


    public static WritableMap createParams(String action, String param1, int param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        params.putInt("param2", param2);
        return params;
    }

    public static WritableMap createParams(String action, ReadableMap param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putMap("param1", param1);
        return params;
    }

    public static WritableMap createParams(String action, Bundle param1) {
        WritableMap params = Arguments.createMap();

        params.putString("action", action);

        if (param1.getBoolean("video")) {
            params.putBoolean("video", true);
        }

        if (param1.getBoolean("audio")) {
            params.putBoolean("audio", true);
        }

        if (param1.getBoolean("desktop")) {
            params.putBoolean("resolution", true);
        }

        params.putInt("resolution", param1.getInt("resolution"));

        return params;
    }

}
