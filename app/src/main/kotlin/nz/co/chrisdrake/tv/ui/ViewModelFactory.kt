package nz.co.chrisdrake.tv.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import nz.co.chrisdrake.tv.ui.listings.ListingsViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ViewModelFactory @Inject constructor(
    viewModelSubcomponent: ViewModelSubcomponent
) : ViewModelProvider.Factory {

  private val creators: Map<Class<*>, () -> ViewModel> = mapOf(
      ListingsViewModel::class.java to { viewModelSubcomponent.listingsViewModel() })

  @Suppress("UNCHECKED_CAST") override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return creators.getValue(modelClass).invoke() as T
  }
}
