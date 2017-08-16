package org.nibiru.oauth.android;

import android.content.Context;
import android.content.Intent;

import org.nibiru.async.core.api.promise.Deferred;
import org.nibiru.async.core.api.promise.Promise;
import org.nibiru.oauth.core.api.OAuth2App;
import org.nibiru.oauth.core.api.OAuth2Exception;
import org.nibiru.oauth.core.api.OAuth2Manager;
import org.nibiru.oauth.core.api.OAuthResponse;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class AndroidOAuth2Manager implements OAuth2Manager {
    private static Deferred<OAuthResponse, OAuth2Exception> currentDeferred;

    public static void onAuth(String code,
                              @Nullable String state) {
        currentDeferred.resolve(new OAuthResponse(code, state));
        currentDeferred = null;
    }

    public static void onError(String error,
                               @Nullable String errorDescription) {
        currentDeferred.reject(new OAuth2Exception(error, errorDescription));
        currentDeferred = null;
    }

    private final Context context;

    @Inject
    public AndroidOAuth2Manager(Context context) {
        this.context = context;
    }

    @Override
    public Promise<OAuthResponse, OAuth2Exception> authenticate(OAuth2App app) {
        checkNotNull(app);
        currentDeferred = Deferred.defer();
        Intent intent = new Intent();
        intent.setClassName(context, OAuth2Activity.class.getName());
        intent.putExtra(OAuth2Activity.AUTH_URL, app.buildAuthUrl());
        intent.putExtra(OAuth2Activity.REDIRECT_URI, app.getRedirectUri());
        context.startActivity(intent);
        return currentDeferred.promise();
    }
}
