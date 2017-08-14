package org.nibiru.oauth.core.api;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class OAuth2Exception extends RuntimeException {
    private final String error;
    private final String errorDescription;

    public OAuth2Exception(String error,
                           @Nullable String errorDescription) {
        this.error = checkNotNull(error);
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    @Nullable
    public String getErrorDescription() {
        return errorDescription;
    }
}
