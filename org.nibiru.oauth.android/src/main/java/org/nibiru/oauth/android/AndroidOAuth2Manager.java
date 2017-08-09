package org.nibiru.oauth.android;


import android.content.Context;
import android.content.Intent;

import org.nibiru.oauth.core.api.OAuth2App;
import org.nibiru.oauth.core.api.OAuth2Manager;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class AndroidOAuth2Manager implements OAuth2Manager {
    private static AuthCallback currentCallback;

    public static void onAuth(String code,
                              @Nullable String state) {
        currentCallback.onAuth(code, state);
        currentCallback = null;
    }

    private final Context context;

    @Inject
    public AndroidOAuth2Manager(Context context) {
        this.context = context;
    }

    @Override
    public void authenticate(OAuth2App app,
                             AuthCallback callback) {
        checkNotNull(app);
        currentCallback = checkNotNull(callback);
        Intent intent = new Intent();
        intent.setClassName(context, OAuth2Activity.class.getName());
        intent.putExtra(OAuth2Activity.AUTH_URL, app.buildAuthUrl());
        intent.putExtra(OAuth2Activity.REDIRECT_URI, app.getRedirectUri());
        context.startActivity(intent);
    }
}
