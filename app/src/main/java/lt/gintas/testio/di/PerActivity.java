package lt.gintas.testio.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Gintautas on 2016-12-11.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
