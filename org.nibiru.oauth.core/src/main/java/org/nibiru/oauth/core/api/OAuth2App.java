package org.nibiru.oauth.core.api;

import javax.annotation.Nullable;

public interface OAuth2App {
    String buildAuthUrl();

    OAuth2Provider getProvider();

    String getClientId();

    String getScope();

    String getRedirectUri();

    @Nullable
    String getState();
}
