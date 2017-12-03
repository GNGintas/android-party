package lt.gintas.testio.ui.mvp.presenter;

import android.os.Bundle;

import lt.gintas.testio.ui.mvp.view.View;

/**
 * Created by Gintautas on 2017-12-02.
 */

public interface Presenter<T extends View> {

    void setView(T view);
    void resume();
    void pause();
    void destroy();

    void save(Bundle outState);

    void restore(Bundle savedInstanceState);

}
