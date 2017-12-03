package lt.gintas.testio.di.module;

import android.app.Activity;
import android.app.Application;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.LoginManager;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.di.PerActivity;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    public LoginManager provideTaxiManager(Application application, AppPreferences preferences,
                                           AppService appService, NetworkStateManager networkStateManager, Gson gson) {
        return new LoginManager(application, preferences, appService, networkStateManager, gson);
    }


}
