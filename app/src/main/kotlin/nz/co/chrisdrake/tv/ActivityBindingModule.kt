package nz.co.chrisdrake.tv

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivity
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivityComponent
import nz.co.chrisdrake.tv.ui.main.MainActivity
import nz.co.chrisdrake.tv.ui.main.MainActivityComponent

@Module(subcomponents = arrayOf(
    MainActivityComponent::class, ChannelsActivityComponent::class)
)
abstract class ActivityBindingModule {
  @Binds @IntoMap @ActivityKey(MainActivity::class)
  abstract fun bindMainActivityFactory(builder: MainActivityComponent.Builder)
      : AndroidInjector.Factory<out Activity>

  @Binds @IntoMap @ActivityKey(ChannelsActivity::class)
  abstract fun bindChannelsActivityFactory(builder: ChannelsActivityComponent.Builder)
      : AndroidInjector.Factory<out Activity>
}
