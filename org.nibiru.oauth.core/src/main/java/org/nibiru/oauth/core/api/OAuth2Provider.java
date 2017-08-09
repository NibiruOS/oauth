package org.nibiru.oauth.core.api;

import javax.annotation.Nullable;

public interface OAuth2Provider {
    String buildAuthUrl(String clientId,
                        String redirectUri,
                        @Nullable String scope,
                        @Nullable String state);
}
