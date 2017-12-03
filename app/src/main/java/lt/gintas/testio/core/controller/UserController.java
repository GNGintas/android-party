package lt.gintas.testio.core.controller;

import android.app.Application;

import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.util.NetworkStateManager;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class UserController implements Controller {

    private AppPreferences appPrefs;
    private User mUser;
    private AppService mService;
    private NetworkStateManager mNetworkStateManager;

    public UserController(Application application, User user, AppService service, NetworkStateManager networkStateManager) {
        appPrefs = new AppPreferences(application);
        mUser = user;
        mService = service;
        mNetworkStateManager = networkStateManager;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

}
