package com.umnix.bookreader;

import android.app.Application;

import com.umnix.bookreader.dagger.ApplicationComponent;
import com.umnix.bookreader.dagger.ApplicationModule;
import com.umnix.bookreader.dagger.DaggerApplicationComponent;

public class BookReaderApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationModule module = new ApplicationModule(this);
        setComponent(DaggerApplicationComponent.builder()
                .applicationModule(module)
                .build());

        getComponent().inject(this);
        module.bootstrap();
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
