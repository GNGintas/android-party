package lt.gintas.testio.core;

import android.util.Pair;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.api.request.LoginRequest;
import lt.gintas.testio.core.api.response.LoginResponse;
import lt.gintas.testio.core.exception.NoNetworkException;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class LoginManager {

    private final AppPreferences mPreferences;
    private final AppService mAppService;
    private final NetworkStateManager mNetworkStateManager;
    private Gson mGson;

    @Inject
    public LoginManager(AppPreferences preferences, AppService appService, NetworkStateManager
            networkStateManager, Gson gson) {
        mPreferences = preferences;
        mAppService = appService;
        mNetworkStateManager = networkStateManager;
        mGson = gson;
    }

    public Flowable<Boolean> login(final String phone, final String pass) {
        return Flowable.just(mNetworkStateManager.isNetworkOnline()).flatMap(new Function<Boolean,
                Flowable<Boolean>>() {
            @Override
            public Flowable<Boolean> apply(Boolean aBoolean) {
                if (aBoolean)
                    return Flowable.just(true);
                return Flowable.error(new NoNetworkException());
            }
        }).retryWhen(new Function<Flowable<? extends Throwable>, Flowable<?>>() {
            @Override
            public Flowable<?> apply(Flowable<? extends Throwable> errorNotification) {
                return errorNotification
                        .zipWith(Flowable.range(1, 5), new BiFunction<Throwable,
                                Integer, Pair<Throwable, Integer>>() {
                            @Override
                            public Pair<Throwable, Integer> apply(Throwable throwable, Integer
                                    attempts) {
                                return new Pair<>(throwable, attempts);
                            }
                        }).flatMap(new Function<Pair<Throwable, Integer>, Flowable<?>>() {
                            @Override
                            public Flowable<?> apply(Pair<Throwable, Integer> attempt) {
                                if (attempt.second == 3)
                                    return Flowable.error(attempt.first);
                                return Flowable.timer(1, TimeUnit.SECONDS);
                            }
                        });
            }
        }).flatMap(new Function<Boolean, Flowable<Boolean>>() {
            @Override
            public Flowable<Boolean> apply(Boolean aBoolean) {
                if (!aBoolean)
                    return Flowable.just(false);
                return mAppService.login(new LoginRequest(phone, pass))
                        .flatMap(new Function<LoginResponse, Flowable<Boolean>>() {
                    @Override
                    public Flowable<Boolean> apply(LoginResponse loginResponse) {
                        final User user = new User(loginResponse);
                        mPreferences.setUser(mGson.toJson(user));
                        return Flowable.just(true);
                    }
                });
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

}
