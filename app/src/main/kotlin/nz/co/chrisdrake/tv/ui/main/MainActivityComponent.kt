package nz.co.chrisdrake.tv.ui.main

import dagger.Subcomponent
import dagger.android.AndroidInjector
import nz.co.chrisdrake.tv.ui.ActivityScope

@Subcomponent @ActivityScope interface MainActivityComponent : AndroidInjector<MainActivity> {
  @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
