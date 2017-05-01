package nz.co.chrisdrake.tv.ui.channels

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nz.co.chrisdrake.tv.ui.ActivityScope

@Subcomponent @ActivityScope interface ChannelsActivityComponent
  : AndroidInjector<ChannelsActivity> {
  @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<ChannelsActivity>()
}
