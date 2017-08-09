package org.nibiru.oauth.core.api;

import javax.annotation.Nullable;

public interface OAuth2Manager {
    interface AuthCallback {
        void onAuth(String code, @Nullable String state);
    }

    void authenticate(OAuth2App app, AuthCallback callback);
}
