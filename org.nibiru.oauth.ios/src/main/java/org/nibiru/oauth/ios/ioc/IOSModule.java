package org.nibiru.oauth.ios.ioc;

import org.nibiru.oauth.core.api.OAuth2Manager;
import org.nibiru.oauth.ios.business.IOSOAuth2Manager;

import dagger.Module;
import dagger.Provides;

@Module
public class IOSModule {
    @Provides
    public OAuth2Manager getOAuth2Manager(IOSOAuth2Manager manager) {
        return manager;
    }
}
