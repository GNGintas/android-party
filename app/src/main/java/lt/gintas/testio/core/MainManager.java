package lt.gintas.testio.core;

import android.app.Application;

import javax.inject.Inject;

import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.controller.UserController;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class MainManager {

    public final Application mApplication;
    private final AppPreferences mPreferences;
    private final AppService mAppService;
    private final NetworkStateManager mNetworkStateManager;
    private UserController mUserController;

    @Inject
    public MainManager(Application application,
                       AppPreferences preferences,
                       AppService appService,
                       NetworkStateManager networkStateManager,
                       UserController userController) {
        mApplication = application;
        mPreferences = preferences;
        mAppService = appService;
        mNetworkStateManager = networkStateManager;
        mUserController = userController;
    }

    public UserController user() {
        return mUserController;
    }

    public void start() {
        mUserController.start();
    }

    public void stop() {
        mUserController.stop();
    }

}
