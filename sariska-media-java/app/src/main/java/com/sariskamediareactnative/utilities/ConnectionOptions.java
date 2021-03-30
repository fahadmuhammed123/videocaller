package org.sariska.sdk;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.URL;

public class ConnectionOptions implements Parcelable {

    private String token;

    public String getToken() {
        return token;
    }
}
