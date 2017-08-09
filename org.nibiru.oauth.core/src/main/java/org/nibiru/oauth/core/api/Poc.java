package org.nibiru.oauth.core.api;

import org.nibiru.oauth.core.impl.BaseOAuth2App;
import org.nibiru.oauth.core.impl.OAuth2Providers;

public class Poc {
    OAuth2App app = new BaseOAuth2App(OAuth2Providers.YAMMER,
            "6Gc3ZzQrjwHJQecoSt9kZQ",
            "http://localhost:8080",
            null,
            null);

    void deleteme() {
        OAuth2Manager o = null;
        o.authenticate(app, (token, state) -> System.out.print("Token: " + token));
    }
}
