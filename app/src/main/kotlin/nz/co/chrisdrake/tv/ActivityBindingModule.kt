package nz.co.chrisdrake.tv

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivity
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivityComponent
import nz.co.chrisdrake.tv.ui.listings.ListingsActivity
import nz.co.chrisdrake.tv.ui.listings.ListingsActivityComponent

@Module(subcomponents = arrayOf(
    ListingsActivityComponent::class, ChannelsActivityComponent::class)
)
abstract class ActivityBindingModule {
  @Binds @IntoMap @ActivityKey(ListingsActivity::class)
  abstract fun bindListingsActivityFactory(builder: ListingsActivityComponent.Builder)
      : AndroidInjector.Factory<out Activity>

  @Binds @IntoMap @ActivityKey(ChannelsActivity::class)
  abstract fun bindChannelsActivityFactory(builder: ChannelsActivityComponent.Builder)
      : AndroidInjector.Factory<out Activity>
}
