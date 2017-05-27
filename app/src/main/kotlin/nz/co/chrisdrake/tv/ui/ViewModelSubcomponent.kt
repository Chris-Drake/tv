package nz.co.chrisdrake.tv.ui

import dagger.Subcomponent
import nz.co.chrisdrake.tv.ui.listings.ListingsViewModel

@Subcomponent interface ViewModelSubcomponent {
  @Subcomponent.Builder interface Builder {
    fun build(): ViewModelSubcomponent
  }

  fun listingsViewModel(): ListingsViewModel
}
