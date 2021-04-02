package org.sariska.sdk;

import com.facebook.react.bridge.ReadableMap;

class Participant {

    public String id;

    public String displayName;

    public String avatar;

    public String email;

    public String name;

    public boolean isLocal;

    public String role;

    public Participant(ReadableMap readableMap) {
        this.id = readableMap.getString("id");
        this.displayName = readableMap.getString("displayName");
        this.avatar = readableMap.getString("avatar");
        this.email = readableMap.getString("email");
        this.name = readableMap.getString("name");
        this.isLocal = readableMap.getBoolean("isLocal");
        this.role = readableMap.getString("role");
    }
}
