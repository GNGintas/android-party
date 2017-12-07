package lt.gintas.testio.di.module;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.di.PerActivity;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Module
public class UserModule {

    @Provides
    @PerActivity
    User provideUser(AppPreferences appPreferences, Gson gson) {
        return gson.fromJson(appPreferences.getUser(), User.class);
    }

}
