package fireminder.fireminder;

import android.app.Application;

import timber.log.Timber;

public class FireminderApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }

}
