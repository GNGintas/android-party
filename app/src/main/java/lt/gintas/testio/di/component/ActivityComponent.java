package lt.gintas.testio.di.component;

import android.app.Activity;

import dagger.Component;
import lt.gintas.testio.di.PerActivity;
import lt.gintas.testio.di.module.ActivityModule;
import lt.gintas.testio.ui.activity.LoginActivity;

/**
 * Created by Gintautas on 2016-12-11.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    //Exposed to sub-graphs.
    Activity activity();
}
