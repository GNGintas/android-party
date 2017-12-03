package lt.gintas.testio.ui.mvp.view;

/**
 * Created by Gintautas on 2017-12-02.
 */

public interface LoginView extends View {

    void onEmptyUsername();
    void onEmptyPassword();
    void onUnknownError();
    void onLoggedIn();

}
