package lt.gintas.testio;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gintautas on 2016-12-11.
 */
public class AppPreferences {
    private static final String PREFERENCES_FILE_NAME = BuildConfig.APPLICATION_ID + "testio_prefs";

    private SharedPreferences mSharedPrefs;

    public AppPreferences(Context context) {
        this.mSharedPrefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void apply(String key, String value) {
        mSharedPrefs.edit().putString(key, value).apply();
    }

    private void apply(String key, boolean value) {
        mSharedPrefs.edit().putBoolean(key, value).apply();
    }

    private void apply(String key, int value) {
        mSharedPrefs.edit().putInt(key, value).apply();
    }

    private void apply(String key, float value) {
        mSharedPrefs.edit().putFloat(key, value).apply();
    }

    private void apply(String key, long value) {
        mSharedPrefs.edit().putLong(key, value).apply();
    }

    private String getString(String key, String defaultValue) {
        return mSharedPrefs.getString(key, defaultValue);
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPrefs.getBoolean(key, defaultValue);
    }

    private int getInt(String key, int defaultValue) {
        return mSharedPrefs.getInt(key, defaultValue);
    }

    private float getFloat(String key, float defaultValue) {
        return mSharedPrefs.getFloat(key, defaultValue);
    }

    private long getLong(String key, long defaultValue) {
        return mSharedPrefs.getLong(key, defaultValue);
    }

    public void setLoggedIn(boolean loggedIn) {
        apply("loggedIn", loggedIn);
    }

    public boolean isLoggedIn() {
        return getBoolean("loggedIn", false);
    }

    public void setUser(String user) {
        apply("user", user);
    }

    public String getUser() {
        return getString("user", "");
    }

}
