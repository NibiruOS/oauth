package org.nibiru.oauth.core.api;


import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

public class OAuthResponse {
    private final String code;
    private final String state;

    public OAuthResponse(String code, @Nullable String state) {
        this.code = Preconditions.checkNotNull(code);
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    @Nullable
    public String getState() {
        return state;
    }
}
