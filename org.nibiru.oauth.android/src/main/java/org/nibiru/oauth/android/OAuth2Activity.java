package org.nibiru.oauth.android;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.nibiru.oauth.core.api.OAuth2Parameters;

public class OAuth2Activity extends Activity {
    static final String AUTH_URL = "authUrl";
    static final String REDIRECT_URI = "redirectUri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        String authUrl = getIntent().getExtras().getString(AUTH_URL);
        Uri redirectUri = Uri.parse(getIntent().getExtras().getString(REDIRECT_URI));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (redirectUri.getScheme().equals(uri.getScheme())
                        && redirectUri.getHost().equals(uri.getHost())
                        && redirectUri.getPort() == uri.getPort()
                        && redirectUri.getPath().equals(uri.getPath())) {
                    String error = uri.getQueryParameter(OAuth2Parameters.ERROR);
                    if (error == null) {

                        AndroidOAuth2Manager.onAuth(uri.getQueryParameter(OAuth2Parameters.CODE),
                                uri.getQueryParameter(OAuth2Parameters.STATE));
                    } else {
                        AndroidOAuth2Manager.onError(error,
                                uri.getQueryParameter(OAuth2Parameters.ERROR_DESCRIPTION));
                    }
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authUrl);
    }
}
