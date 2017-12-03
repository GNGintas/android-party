package lt.gintas.testio.ui.mvp.presenter;

import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lt.gintas.testio.core.LoginManager;
import lt.gintas.testio.ui.mvp.view.LoginView;

/**
 * Created by Gintautas on 2017-12-02.
 */

public class LoginPresenter implements Presenter<LoginView> {

    private LoginManager mManager;
    private LoginView mView;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    @Inject
    public LoginPresenter(LoginManager manager) {
        mManager = manager;
    }

    @Override
    public void setView(LoginView view) {
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
        if (mSubscriptions != null && !mSubscriptions.isDisposed())
            mSubscriptions.dispose();
    }

    @Override
    public void save(Bundle outState) {
    }

    @Override
    public void restore(Bundle savedInstanceState) {
    }

    public void login(final String phone, final String password, final boolean rememberMe) {
        if (phone.isEmpty() || password.isEmpty()) {
            if (phone.isEmpty())
                mView.onEmptyUsername();
            if (password.isEmpty())
                mView.onEmptyPassword();
            return;
        }

        mSubscriptions.add(mManager.login(phone, password, rememberMe).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext(new Function<Throwable, Flowable<? extends Boolean>>() {
                @Override
                public Flowable<? extends Boolean> apply(Throwable throwable) {
                    mView.onUnknownError();
                    return Flowable.just(false);
                }
            }).doOnComplete(new Action() {
                @Override
                public void run() throws Exception {
                    mView.onHideLoading();
                }
            }).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean)
                        mView.onLoggedIn();
                }
        }));
    }

}
