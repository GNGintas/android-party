package lt.gintas.testio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lt.gintas.testio.R;
import lt.gintas.testio.core.MainManager;
import lt.gintas.testio.core.model.ServerItem;
import lt.gintas.testio.core.model.User;
import lt.gintas.testio.di.Dagger2Helper;
import lt.gintas.testio.di.HasComponent;
import lt.gintas.testio.di.component.UserComponent;
import lt.gintas.testio.di.module.UserModule;
import lt.gintas.testio.ui.adapter.ServersAdapter;
import lt.gintas.testio.ui.mvp.presenter.MainPresenter;
import lt.gintas.testio.ui.mvp.view.MainView;

public class MainActivity extends BaseActivity implements MainView, HasComponent<UserComponent> {

    private UserComponent mUserComponent;

    @Inject
    MainPresenter mPresenter;

    @Inject
    MainManager mManager;

    @Inject
    User mUser;

    @BindView(R.id.serversList)
    RecyclerView serversList;

    List<ServerItem> serverItems = new ArrayList<>();
    ServersAdapter serversAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mPresenter.setView(this);

        serversList.setLayoutManager(new LinearLayoutManager(this));
        serversAdapter = new ServersAdapter(serverItems);
        serversList.setAdapter(serversAdapter);

        mPresenter.getServerItems(getResources().getString(R.string.token_secret) + " "
                + mUser.getToken());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.destroy();
    }

    private void initializeInjector() {
        mUserComponent = Dagger2Helper.createComponent(UserComponent.class, getAppComponent(),
                getActivityModule(), new UserModule());
        mUserComponent.inject(this);
    }

    @Override
    public UserComponent getComponent() {
        if (mUserComponent == null)
            initializeInjector();
        return mUserComponent;
    }

    @Override
    public void onShowLoading() {
        mProgress.show();
    }

    @Override
    public void onHideLoading() {
        mProgress.hide();
    }

    @OnClick(R.id.logoutBtn)
    public void onLogout() {
        mUser = null;
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        this.finish();
    }

    @Override
    public void onServerListLoaded(List<ServerItem> serverItems) {
        this.serverItems.clear();
        this.serverItems.addAll(serverItems);
        serversAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        shortToast(getResources().getString(R.string.error));
    }
}
