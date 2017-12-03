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

    public boolean isConnectedOrConnecting(){
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isNetworkOnline() {
        NetworkInfo i = connectivityManager.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

    public boolean isWifi() {
        if (isNetworkOnline()) {
            NetworkInfo i = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (i.isConnected())
                return true;
        }
        return false;
    }

    public boolean isMobile() {
        if (isNetworkOnline()) {
            NetworkInfo i = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (i.isConnected())
                return true;
        }
        return false;
    }
}
