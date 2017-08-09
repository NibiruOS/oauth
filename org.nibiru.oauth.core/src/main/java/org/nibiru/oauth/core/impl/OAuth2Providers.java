package org.nibiru.oauth.core.impl;


import org.nibiru.oauth.core.api.OAuth2Provider;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.nibiru.oauth.core.api.OAuth2Parameters.CLIENT_ID;
import static org.nibiru.oauth.core.api.OAuth2Parameters.REDIRECT_URI;
import static org.nibiru.oauth.core.api.OAuth2Parameters.SCOPE;
import static org.nibiru.oauth.core.api.OAuth2Parameters.STATE;

public enum OAuth2Providers implements OAuth2Provider {
    YAMMER("https://www.yammer.com/oauth2/authorize");

    private final String authUrl;

    OAuth2Providers(String authUrl) {
        this.authUrl = authUrl;
    }

    @Override
    public String buildAuthUrl(String clientId,
                               String redirectUri,
                               @Nullable String scope,
                               @Nullable String state) {
        checkNotNull(clientId);
        checkNotNull(redirectUri);
        StringBuilder sb = new StringBuilder();
        sb.append(authUrl);
        addParam(sb, CLIENT_ID, clientId, "?");
        addParam(sb, REDIRECT_URI, redirectUri, "&");
        addParam(sb, SCOPE, scope, "&");
        addParam(sb, STATE, state, "&");

        return sb.toString();
    }

    private void addParam(StringBuilder sb,
                          String key,
                          String value,
                          String separator) {
        if (value != null) {
            sb.append(separator);
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
    }
}

