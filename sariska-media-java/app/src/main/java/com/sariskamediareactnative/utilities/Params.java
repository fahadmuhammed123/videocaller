import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

public class Params {
    public static createParams(String action) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, String param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, Boolean param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putBoolean("param1", param1);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, Number param1) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, String param1, String param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        params.putString("param2", param2);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, Number param1, Number param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putInt("param2", param2);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, Number param1, Boolean param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putBoolean("param2", param2);
        BroadcastNativeEvent.sendEvent(action, params);
    }

    public static createParams(String action, Number param1, String param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putInt("param1", param1);
        params.putString("param2", param2);
        BroadcastNativeEvent.sendEvent(action, params);
    }


    public static createParams(String action, String param1, Number param2) {
        WritableMap params = Arguments.createMap();
        params.putString("action", action);
        params.putString("param1", param1);
        params.putInt("param2", param2);
        BroadcastNativeEvent.sendEvent(action, params);
    }
}