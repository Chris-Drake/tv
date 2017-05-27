package nz.co.chrisdrake.tv

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import dagger.Module
import dagger.Provides
import nz.co.chrisdrake.tv.ui.ViewModelFactory
import nz.co.chrisdrake.tv.ui.ViewModelSubcomponent
import javax.inject.Singleton

@Module(subcomponents = arrayOf(ViewModelSubcomponent::class))
class ApplicationModule(private val application: TvApplication) {
  @Provides @Singleton fun provideAppContext(): Context = application

  @Provides @Singleton fun provideViewModelFactory(
      viewModelSubcomponent: ViewModelSubcomponent.Builder): ViewModelProvider.Factory {
    return ViewModelFactory(viewModelSubcomponent.build());
  }
}
