package lt.gintas.testio.di.module;

import android.app.Application;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.MainManager;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.controller.UserController;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.di.PerActivity;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Module
public class UserModule {

    @Provides
    @PerActivity
    User provideUser(AppPreferences appPreferences, Gson gson) {
        String user = appPreferences.getUser();
        if (user.isEmpty()) {
            user = "{mDriverId=\"\"}";
        }
        return gson.fromJson(user, User.class);
    }

    @Provides
    @PerActivity
    UserController provideUserController(Application app,
                                         User user,
                                         AppService service,
                                         NetworkStateManager networkStateManager) {
        return new UserController(app,
                user,
                service,
                networkStateManager);
    }

    @Provides
    @PerActivity
    public MainManager provideTaxiManager(Application application,
                                          AppPreferences preferences,
                                          AppService appService,
                                          NetworkStateManager networkStateManager,
                                          UserController userController) {
        return new MainManager(application, preferences, appService, networkStateManager, userController);
    }
}
