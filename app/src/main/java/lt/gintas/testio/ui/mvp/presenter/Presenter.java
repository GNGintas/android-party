package lt.gintas.testio.ui.mvp.presenter;

import lt.gintas.testio.ui.mvp.view.View;

/**
 * Created by Gintautas on 2017-12-02.
 */

public interface  Presenter<T extends View> {

    void setView(T view);
    void destroy();

}
