package org.nibiru.oauth.gwt.business;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;

import org.nibiru.async.core.api.promise.Deferred;
import org.nibiru.async.core.api.promise.Promise;
import org.nibiru.oauth.core.api.OAuth2App;
import org.nibiru.oauth.core.api.OAuth2Exception;
import org.nibiru.oauth.core.api.OAuth2Manager;
import org.nibiru.oauth.core.api.OAuth2Parameters;
import org.nibiru.oauth.core.api.OAuthResponse;

import java.util.Date;

import javax.inject.Inject;

public class GwtOAuth2Manager implements OAuth2Manager {
    @Inject
    public GwtOAuth2Manager() {
    }

    @Override
    public Promise<OAuthResponse, OAuth2Exception> authenticate(OAuth2App app) {
        Deferred<OAuthResponse, OAuth2Exception> deferred = Deferred.defer();

        JavaScriptObject popup = openDocument(app.buildAuthUrl());

        new Timer() {
            @Override
            public void run() {
                try {
                    if (getUrl(popup).startsWith(app.getRedirectUri())) {
                        finish();
                        String error = getQueryVariable(popup, OAuth2Parameters.ERROR);
                        if (error == null) {
                            deferred.resolve(new OAuthResponse(getQueryVariable(popup, OAuth2Parameters.CODE),
                                    getQueryVariable(popup, OAuth2Parameters.STATE)));
                        } else {
                            deferred.reject(new OAuth2Exception(error,
                                    getQueryVariable(popup, OAuth2Parameters.ERROR_DESCRIPTION)));
                        }
                    }
                } catch (Exception e) {
                    finish();
                    deferred.reject(new OAuth2Exception("",
                            e.getMessage()));
                }
            }

            private void finish(){
                cancel();
                close(popup);
            }
        }.scheduleRepeating(100);
        return deferred.promise();
    }

    private native JavaScriptObject openDocument(String targetUrl) /*-{
        var win = window.open(targetUrl, '_blank', 'resizeable,scrollbars');
        return win;
    }-*/;

    private native String getUrl(JavaScriptObject targetWindow) /*-{
        return targetWindow.location.href;
    }-*/;

    private native String getQueryVariable(JavaScriptObject targetWindow, String param) /*-{
        var query = targetWindow.location.search.substring(1);
        var vars = query.split('&');
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split('=');
            if (decodeURIComponent(pair[0]) == param) {
                return decodeURIComponent(pair[1]);
            }
        }
        return null;
    }-*/;

    private native String close(JavaScriptObject targetWindow) /*-{
        targetWindow.close();
    }-*/;
}
