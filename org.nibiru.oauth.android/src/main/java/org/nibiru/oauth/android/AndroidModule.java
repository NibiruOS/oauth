package org.nibiru.oauth.android;

import org.nibiru.oauth.core.api.OAuth2Manager;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
    @Provides
    public OAuth2Manager getOAuth2Manager(AndroidOAuth2Manager manager) {
        return manager;
    }
}
