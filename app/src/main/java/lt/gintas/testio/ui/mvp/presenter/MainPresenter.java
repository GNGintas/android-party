package lt.gintas.testio.ui.mvp.presenter;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.core.MainManager;
import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.model.ServerItem;
import lt.gintas.testio.ui.mvp.view.MainView;
import lt.gintas.testio.util.NetworkStateManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gintautas on 2016-12-13.
 */

public class MainPresenter implements Presenter<MainView> {

    private NetworkStateManager mNetworkStateManager;
    private AppService mAppService;
    private AppPreferences appPrefs;

    private MainView mView;

    @Inject
    public MainPresenter(NetworkStateManager networkStateManager, MainManager manager, AppService appService) {
        mNetworkStateManager = networkStateManager;
        mAppService = appService;
        appPrefs = new AppPreferences(manager.mApplication.getApplicationContext());
    }

    @Override
    public void setView(MainView view) {
        mView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void save(Bundle outState) {
    }

    @Override
    public void restore(Bundle savedInstanceState) {
    }

    public void getServerItems(final String token) {
        mView.onShowLoading();
        mAppService.getServersList(token).enqueue(new Callback<List<ServerItem>>() {
            @Override
            public void onResponse(Call<List<ServerItem>> call, Response<List<ServerItem>> response) {
                mView.onServerListLoaded(response.body());
                mView.onHideLoading();
            }

            @Override
            public void onFailure(Call<List<ServerItem>> call, Throwable t) {
                t.printStackTrace();
                mView.onError();
                mView.onHideLoading();
            }
        });
    }

}
