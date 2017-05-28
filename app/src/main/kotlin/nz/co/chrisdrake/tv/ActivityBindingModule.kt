package nz.co.chrisdrake.tv

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.chrisdrake.tv.ui.ActivityScope
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivity
import nz.co.chrisdrake.tv.ui.listings.ListingsActivity

@Module abstract class ActivityBindingModule {
  @ContributesAndroidInjector @ActivityScope
  abstract fun contributeListingsActivityInjector(): ListingsActivity

  @ContributesAndroidInjector @ActivityScope
  abstract fun contributeChannelsActivityInjector(): ChannelsActivity
}
