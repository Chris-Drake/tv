package nz.co.chrisdrake.tv.ui.listings

import io.reactivex.disposables.Disposable
import nz.co.chrisdrake.tv.domain.interactor.GetWhatsOnNow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ListingsActionCreator @Inject constructor(
    private val store: ListingsStore,
    private val listingsUseCase: GetWhatsOnNow
) {
  private var listingsDisposable: Disposable? = null

  fun fetchListings() {
    listingsDisposable?.dispose()
    listingsDisposable = listingsUseCase.buildObservable().subscribe(store)
  }
}