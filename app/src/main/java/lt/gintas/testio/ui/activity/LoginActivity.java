package lt.gintas.testio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lt.gintas.testio.R;
import lt.gintas.testio.di.Dagger2Helper;
import lt.gintas.testio.di.HasComponent;
import lt.gintas.testio.di.component.ActivityComponent;
import lt.gintas.testio.ui.mvp.presenter.LoginPresenter;
import lt.gintas.testio.ui.mvp.view.LoginView;

/**
 * Created by Gintautas on 2016-12-11.
 */

public class LoginActivity extends BaseActivity implements HasComponent<ActivityComponent>, LoginView {

    private ActivityComponent mActivityComponent;

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.username)
    EditText mPhone;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.loginBtn)
    TextView mLoginButton;

    @BindView(R.id.mainLogo)
    ImageView mainLogo;

    @BindView(R.id.loginForm)
    LinearLayout loginForm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeInjector();

        mLoginPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLoginPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.destroy();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void initializeInjector() {
        mActivityComponent = Dagger2Helper.createComponent(ActivityComponent.class, getAppComponent(), getActivityModule());
        mActivityComponent.inject(this);
    }

    @Override
    public ActivityComponent getComponent() {
        if (mActivityComponent == null)
            initializeInjector();
        return mActivityComponent;
    }

    @Override
    public void onShowLoading() {
        mProgress.show();
        mLoginButton.setEnabled(false);
    }

    @Override
    public void onHideLoading() {
        mProgress.hide();
        mLoginButton.setEnabled(true);
    }

    @OnClick(R.id.loginBtn)
    public void onLogin() {
        YoYo.with(Techniques.FadeOutUp)
                .duration(500)
                .playOn(mainLogo);
        YoYo.with(Techniques.FadeOutDown)
                .duration(500)
                .playOn(loginForm);
        onShowLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginPresenter.login(mPhone.getText().toString(), mPassword.getText().toString(), true);
            }
        }, 500);
    }

    @Override
    public void onEmptyUsername() {
        shortToast(getResources().getString(R.string.empty_username));
        onGetLoginForm();
    }

    @Override
    public void onEmptyPassword() {
        shortToast(getResources().getString(R.string.empty_password));
        onGetLoginForm();
    }

    @Override
    public void onUnknownError() {
        shortToast(getResources().getString(R.string.error));
        onGetLoginForm();
    }

    private void onGetLoginForm() {
        onHideLoading();
        YoYo.with(Techniques.FadeInDown)
                .duration(500)
                .playOn(mainLogo);
        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(loginForm);
    }

    @Override
    public void onLoggedIn() {
        onHideLoading();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        this.finish();
    }
}
