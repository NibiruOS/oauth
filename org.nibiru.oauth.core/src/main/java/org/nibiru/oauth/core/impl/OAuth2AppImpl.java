package org.nibiru.oauth.core.impl;

import org.nibiru.oauth.core.api.OAuth2App;
import org.nibiru.oauth.core.api.OAuth2Provider;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;


public class OAuth2AppImpl implements OAuth2App {
    private final OAuth2Provider provider;
    private final String clientId;
    private final String redirectUri;
    private final String scope;
    private final String state;

    public OAuth2AppImpl(OAuth2Provider provider,
                         String clientId,
                         String redirectUri,
                         @Nullable String scope,
                         @Nullable String state) {
        this.provider = checkNotNull(provider);
        this.clientId = checkNotNull(clientId);
        this.redirectUri = checkNotNull(redirectUri);
        this.scope = scope;
        this.state = state;
    }

    @Override
    public String buildAuthUrl() {
        return provider.buildAuthUrl(clientId,
                redirectUri,
                scope,
                state);
    }

    @Override
    public OAuth2Provider getProvider() {
        return provider;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Nullable
    @Override
    public String getState() {
        return state;
    }
}
