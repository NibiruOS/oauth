package org.nibiru.oauth.gwt.ioc;

import org.nibiru.oauth.core.api.OAuth2Manager;
import org.nibiru.oauth.gwt.business.GwtOAuth2Manager;

import dagger.Module;
import dagger.Provides;

@Module
public class GwtModule {
    @Provides
    public OAuth2Manager getOAuth2Manager(GwtOAuth2Manager manager) {
        return manager;
    }
}
