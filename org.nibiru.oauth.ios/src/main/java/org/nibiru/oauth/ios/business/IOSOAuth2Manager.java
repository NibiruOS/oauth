package org.nibiru.oauth.ios.business;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import org.nibiru.async.core.api.promise.Deferred;
import org.nibiru.async.core.api.promise.Promise;
import org.nibiru.oauth.core.api.OAuth2App;
import org.nibiru.oauth.core.api.OAuth2Exception;
import org.nibiru.oauth.core.api.OAuth2Manager;
import org.nibiru.oauth.core.api.OAuth2Parameters;
import org.nibiru.oauth.core.api.OAuthResponse;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import apple.coregraphics.struct.CGPoint;
import apple.coregraphics.struct.CGRect;
import apple.coregraphics.struct.CGSize;
import apple.foundation.NSURL;
import apple.foundation.NSURLRequest;
import apple.uikit.UIApplication;
import apple.uikit.UINavigationController;
import apple.uikit.UIViewController;
import apple.uikit.UIWebView;
import apple.uikit.UIWindow;
import apple.uikit.protocol.UIWebViewDelegate;

import static com.google.common.base.Preconditions.checkNotNull;

public class IOSOAuth2Manager implements OAuth2Manager {
    @Inject
    public IOSOAuth2Manager() {
    }

    @Override
    public Promise<OAuthResponse, OAuth2Exception> authenticate(OAuth2App app) {
        checkNotNull(app);
        Deferred<OAuthResponse, OAuth2Exception> deferred = Deferred.defer();

        UIWindow mainWindow = UIApplication.sharedApplication()
                .keyWindow();
        UINavigationController navigationController = (UINavigationController) mainWindow
                .rootViewController();

        UIViewController viewController = UIViewController.alloc().init();

        UIWebView webView = UIWebView.alloc().init();
        webView.setFrame(new CGRect(new CGPoint(0, 0),
                new CGSize(mainWindow.frame().size().width(),
                        mainWindow.frame().size().height())));

        NSURL redirectUrl = NSURL.URLWithString(app.getRedirectUri());

        webView.setDelegate(new UIWebViewDelegate() {
            @Override
            public boolean webViewShouldStartLoadWithRequestNavigationType(UIWebView webView,
                                                                           NSURLRequest request,
                                                                           long navigationType) {
                NSURL url = request.mainDocumentURL();
                if (redirectUrl.scheme().equals(url.scheme())
                        && redirectUrl.host().equals(url.host())
                        && redirectUrl.port() == url.port()
                        && redirectUrl.path().equals(url.path())) {

                    Map<String, String> params = queryDictionary(url);

                    String error = params.get(OAuth2Parameters.ERROR);
                    if (error == null) {
                        deferred.resolve(new OAuthResponse(params.get(OAuth2Parameters.CODE),
                                params.get(OAuth2Parameters.STATE)));
                    } else {
                        deferred.reject(new OAuth2Exception(error,
                                params.get(OAuth2Parameters.ERROR_DESCRIPTION)));
                    }
                    navigationController.popViewControllerAnimated(true);
                    return false;
                } else {
                    return true;
                }
            }
        });
        viewController.setView(webView);

        navigationController.pushViewControllerAnimated(viewController, true);

        webView.loadRequest(NSURLRequest.requestWithURL(NSURL.URLWithString(app.buildAuthUrl())));

        return deferred.promise();
    }

    private Map<String, String> queryDictionary(NSURL url) {
        Map<String, String> params = Maps.newHashMap();
        for (String paramValue : Splitter.on('&').split(url.query())) {
            Iterator<String> iterator = Splitter.on('=').split(paramValue).iterator();
            String key = iterator.next();
            String value = iterator.next();
            params.put(key, value);
        }
        return params;
    }
}
