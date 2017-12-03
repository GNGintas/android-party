package lt.gintas.testio.core;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lt.gintas.testio.AppPreferences;
import okhttp3.OkHttpClient;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Module
public class CoreModule {

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    AppPreferences provideSharedPreferences(Application application) {
        return new AppPreferences(application);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createOkHttpClient();
    }

}
