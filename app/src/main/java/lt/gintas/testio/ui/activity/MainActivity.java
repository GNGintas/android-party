package lt.gintas.testio.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import lt.gintas.testio.R;
import lt.gintas.testio.di.Dagger2Helper;
import lt.gintas.testio.di.HasComponent;
import lt.gintas.testio.di.component.UserComponent;
import lt.gintas.testio.di.module.UserModule;
import lt.gintas.testio.ui.fragment.ServersListFragment;

public class MainActivity extends BaseActivity implements HasComponent<UserComponent> {

    private UserComponent mUserComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ServersListFragment()).commit();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
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

    @OnClick(R.id.logoutBtn)
    public void onLogout() {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        finish();
    }
}
