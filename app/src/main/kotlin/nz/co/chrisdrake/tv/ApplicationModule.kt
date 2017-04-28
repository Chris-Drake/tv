package nz.co.chrisdrake.tv

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ApplicationModule(private val application: TvApplication) {
  @Provides @Singleton fun provideAppContext(): Context = application
}
