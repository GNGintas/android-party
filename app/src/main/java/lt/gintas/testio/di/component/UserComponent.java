package lt.gintas.testio.di.component;

import dagger.Component;
import lt.gintas.testio.di.PerActivity;
import lt.gintas.testio.di.module.ActivityModule;
import lt.gintas.testio.di.module.UserModule;
import lt.gintas.testio.ui.activity.LoginActivity;
import lt.gintas.testio.ui.activity.MainActivity;

/**
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
}
