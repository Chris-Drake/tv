package nz.co.chrisdrake.tv

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class TvApplication : Application(), HasActivityInjector {

  @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    AndroidThreeTen.init(this)

    DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(application = this))
        .build()
        .inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}
