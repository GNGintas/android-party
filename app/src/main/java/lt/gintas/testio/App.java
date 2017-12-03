package lt.gintas.testio;

import android.app.Application;

import lt.gintas.testio.core.CoreModule;
import lt.gintas.testio.core.api.ApiModule;
import lt.gintas.testio.core.controller.ControllerModule;
import lt.gintas.testio.di.Dagger2Helper;
import lt.gintas.testio.di.component.AppComponent;
import lt.gintas.testio.di.module.AppModule;
import timber.log.Timber;

/**
 * Created by Gintautas on 2016-12-11.
 */

public class App extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setComponent();

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    public void setComponent() {
        mComponent = Dagger2Helper.createComponent(AppComponent.class, new AppModule(this),
                new SystemServiceModule(),
                new CoreModule(),
                new ApiModule(),
                new ControllerModule());
    }

}
