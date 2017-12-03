package lt.gintas.testio.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import io.reactivex.Observable;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.di.HasComponent;
import lt.gintas.testio.ui.activity.BaseActivity;
import lt.gintas.testio.ui.dialog.LoadingDialog;

/**
 * Created by Gintautas on 2016-12-13.
 */

public class BaseFragment extends Fragment {

    protected static final String sDateFormat = "yyyy-MM-dd";
    protected static final String sHoursFormat = "HH:mm";

    protected AppPreferences appPrefs;
    LoadingDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPrefs = new AppPreferences(getActivity());

        mProgress = new LoadingDialog.Builder(getActivity()).build();
    }

    protected void shortToast(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void longToast(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }

    public Observable<Boolean> dialog(String titleRes, String messageRes, String confirmRes, String cancelRes) {
        if (getActivity() instanceof BaseActivity)
            return ((BaseActivity) getActivity()).dialog(titleRes, messageRes, confirmRes, cancelRes);
        return Observable.empty();
    }

}
