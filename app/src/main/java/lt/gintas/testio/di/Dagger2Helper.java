package lt.gintas.testio.di;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class Dagger2Helper {

    private static HashMap<Class<?>, HashMap<Class<?>, Method>> methodsCache = new HashMap<>();

    public static void inject(Class<?> componentClass, Object component, Object target) {
        HashMap<Class<?>, Method> methods = methodsCache.get(componentClass);
        if (methods == null)
            throw new RuntimeException("Component " + componentClass + " has not been built with " + Dagger2Helper.class);

        Class targetClass = target.getClass();
        Method method = methods.get(targetClass);
        if (method == null)
            throw new RuntimeException("Method for " + targetClass + " injection does not exist in " + componentClass);

        try {
            method.invoke(component, target);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Magic method that creates a component with its dependencies set, by reflection. Relies on
     * Dagger2 naming conventions.
     */
    public static <T> T createComponent(Class<T> componentClass, Object... dependencies) {
        buildMethodsCache(componentClass);

        String fqn = componentClass.getName();

        String packageName = componentClass.getPackage().getName();
        String simpleName = fqn.substring(packageName.length() + 1);
        String generatedName = (packageName + ".Dagger" + simpleName).replace('$', '_');

        try {
            Class<?> generatedClass = Class.forName(generatedName);
            Object builder = generatedClass.getMethod("builder").invoke(null);

            for (Method method : builder.getClass().getMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1) {
                    Class<?> dependencyClass = params[0];
                    for (Object dependency : dependencies) {
                        if (dependencyClass.isAssignableFrom(dependency.getClass())) {
                            method.invoke(builder, dependency);
                            break;
                        }
                    }
                }
            }
            //noinspection unchecked
            return (T) builder.getClass().getMethod("build").invoke(builder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void buildMethodsCache(Class<T> componentClass) {
        if (!Dagger2Helper.methodsCache.containsKey(componentClass)) {
            HashMap<Class<?>, Method> methods = new HashMap<>();
            for (Method method : componentClass.getMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1)
                    methods.put(params[0], method);
            }
            Dagger2Helper.methodsCache.put(componentClass, methods);
        }
    }
}
