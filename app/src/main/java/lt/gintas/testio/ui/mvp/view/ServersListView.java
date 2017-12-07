package lt.gintas.testio.ui.mvp.view;

import java.util.List;

import lt.gintas.testio.core.model.ServerItem;

/**
 * Created by gintautas on 07/12/2017.
 */

public interface ServersListView extends View {

    void onServerListLoaded(List<ServerItem> serverItems);
    void onError();

}
