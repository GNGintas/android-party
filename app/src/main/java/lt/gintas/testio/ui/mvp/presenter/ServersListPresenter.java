package lt.gintas.testio.ui.mvp.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import lt.gintas.testio.core.api.AppService;
import lt.gintas.testio.core.model.ServerItem;
import lt.gintas.testio.ui.mvp.view.ServersListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gintautas on 07/12/2017.
 */

public class ServersListPresenter implements Presenter<ServersListView> {

    private AppService mAppService;
    private ServersListView mView;

    @Inject
    public ServersListPresenter(AppService appService) {
        mAppService = appService;
    }

    @Override
    public void setView(ServersListView view) {
        mView = view;
    }

    @Override
    public void destroy() {
        mView = null;
    }

    public void getServerItems(final String token) {
        mView.onShowLoading();
        mAppService.getServersList(token).enqueue(new Callback<List<ServerItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerItem>> call, @NonNull Response<List<ServerItem>> response) {
                mView.onServerListLoaded(response.body());
                mView.onHideLoading();
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerItem>> call, @NonNull Throwable t) {
                mView.onError();
                mView.onHideLoading();
            }
        });
    }

}
