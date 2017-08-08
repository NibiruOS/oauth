package org.nibiru.oauth.core.api;

public interface OAuth2Manager<T extends Enum<?>> {
    enum AuthProvider {
        YAMMER,
        FACEBOOK,
        GOOGLE,
        INSTAGRAM
    }

    OAuth2 get(T appId, AuthProvider provider);

    void addApp(T appId,
                AuthProvider provider,
                String clientId,
                String redirectUri,
                String responseType);
}
