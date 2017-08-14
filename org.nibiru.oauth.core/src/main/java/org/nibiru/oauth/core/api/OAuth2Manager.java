package org.nibiru.oauth.core.api;

import org.nibiru.async.core.api.promise.Promise;

public interface OAuth2Manager {
    Promise<OAuthResponse, OAuth2Exception> authenticate(OAuth2App app);
}
