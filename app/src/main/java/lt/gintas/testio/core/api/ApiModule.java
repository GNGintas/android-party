package lt.gintas.testio.core.api;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;
import lt.gintas.testio.R;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    AppService provideAppService(Application application) {
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.backend_url))
                .client(getOkHttpClient(application.getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(AppService.class);
    }

    private Interceptor headerAuthorizationInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Headers headers = request.headers().newBuilder().build();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        }
    };

    private OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.addInterceptor(headerAuthorizationInterceptor);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        final @Nullable File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            okClientBuilder.cache(new Cache(cacheDir, 1024));
        }
        okClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(10, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }
}
