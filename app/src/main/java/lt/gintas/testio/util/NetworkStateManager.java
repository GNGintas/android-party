package lt.gintas.testio.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class NetworkStateManager {

    private final ConnectivityManager connectivityManager;

    public NetworkStateManager(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public boolean isNetworkOnline() {
        NetworkInfo i = connectivityManager.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

}
