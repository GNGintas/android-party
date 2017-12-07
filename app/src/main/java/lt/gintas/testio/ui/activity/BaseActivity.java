package lt.gintas.testio.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import lt.gintas.testio.App;
import lt.gintas.testio.AppPreferences;
import lt.gintas.testio.di.component.AppComponent;
import lt.gintas.testio.di.module.ActivityModule;
import lt.gintas.testio.ui.dialog.LoadingDialog;

/**
 * Created by Gintautas on 2016-12-11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected AppPreferences appPrefs;
    LoadingDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        getAppComponent().inject(this);
        ButterKnife.bind(this);
        appPrefs = new AppPreferences(this);

        mProgress = new LoadingDialog.Builder(this).build();
    }

    protected abstract int getLayoutResId();

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void shortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
