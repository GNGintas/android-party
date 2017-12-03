package lt.gintas.testio.ui.mvp.view;

import java.util.List;

import lt.gintas.testio.core.model.ServerItem;

/**
 * Created by Gintautas on 2016-12-13.
 */

public interface MainView extends View {

    void onServerListLoaded(List<ServerItem> serverItems);

    void onError();

}
