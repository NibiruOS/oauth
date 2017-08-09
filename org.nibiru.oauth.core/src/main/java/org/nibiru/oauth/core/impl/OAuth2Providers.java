package org.nibiru.oauth.core.impl;


import org.nibiru.oauth.core.api.OAuth2Provider;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

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
        addParam(sb, Constants.CLIENT_ID_KEY, clientId, "?");
        addParam(sb, Constants.REDIRECT_URI_KEY, redirectUri, "&");
        addParam(sb, Constants.SCOPE_KEY, scope, "&");
        addParam(sb, Constants.STATE_KEY, state, "&");

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

    interface Constants {
        String CLIENT_ID_KEY = "client_id";
        String REDIRECT_URI_KEY = "redirect_uri";
        String SCOPE_KEY = "scope";
        String STATE_KEY = "state";
    }
}

