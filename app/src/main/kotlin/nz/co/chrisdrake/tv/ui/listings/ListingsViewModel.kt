package nz.co.chrisdrake.tv.ui.listings

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import nz.co.chrisdrake.tv.domain.interactor.GetWhatsOnNow
import nz.co.chrisdrake.tv.ui.LiveDataStore
import javax.inject.Inject

class ListingsViewModel @Inject constructor(
    private val listingsUseCase: GetWhatsOnNow
) : ViewModel() {

  private val dataStore = LiveDataStore(
      reducer = ListingsUiModel::reduce,
      initialState = ListingsUiModel())

  private var listingsDisposable: Disposable? = null

  fun getState() = dataStore.data()

  fun refreshListings() {
    listingsDisposable?.dispose()
    listingsDisposable = listingsUseCase.buildObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(dataStore::accept)
  }

  override fun onCleared() {
    listingsDisposable?.dispose()
  }
}
