package nz.co.chrisdrake.tv.ui.listings

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nz.co.chrisdrake.tv.ui.ActivityScope

@Subcomponent @ActivityScope interface ListingsActivityComponent : AndroidInjector<ListingsActivity> {
  @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<ListingsActivity>()
}
