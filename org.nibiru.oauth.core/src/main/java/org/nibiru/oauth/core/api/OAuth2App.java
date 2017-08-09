package org.nibiru.oauth.core.api;

public interface OAuth2App {
    String buildAuthUrl();
    String getRedirectUri();
}
