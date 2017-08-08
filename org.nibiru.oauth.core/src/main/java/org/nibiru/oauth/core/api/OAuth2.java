package org.nibiru.oauth.core.api;

import java.util.function.Consumer;

public interface OAuth2 {
    void authenticate(Consumer<String> tokenCallback);
}
