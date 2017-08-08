package org.nibiru.oauth.core.api;

public class Poc {
    enum Apps {CORE_VALUE}

    void caca() {
        OAuth2Manager<Apps> o = null;
        o.get(Apps.CORE_VALUE, OAuth2Manager.AuthProvider.YAMMER)
                .authenticate((token) -> System.out.print("Token: " + token));
    }

}
