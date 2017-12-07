package lt.gintas.testio.di.component;

import dagger.Component;
import lt.gintas.testio.di.PerActivity;
import lt.gintas.testio.di.module.ActivityModule;
import lt.gintas.testio.di.module.UserModule;
import lt.gintas.testio.ui.activity.MainActivity;
import lt.gintas.testio.ui.fragment.ServersListFragment;

/**
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(MainActivity activity);
    void inject(ServersListFragment fragment);
}
