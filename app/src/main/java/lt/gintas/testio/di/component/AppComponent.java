package lt.gintas.testio.di.component;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.SystemServiceModule;
import lt.gintas.testio.core.CoreModule;
import lt.gintas.testio.core.api.ApiModule;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.di.module.AppModule;
import lt.gintas.testio.ui.activity.BaseActivity;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {AppModule.class, SystemServiceModule.class, CoreModule.class, ApiModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);

    Application application();
    AppPreferences appPreferences();
    AppService appService();
    NetworkStateManager networkStateManager();
    Gson gson();
}
