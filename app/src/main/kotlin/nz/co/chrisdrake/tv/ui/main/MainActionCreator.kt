package nz.co.chrisdrake.tv.ui.main

import io.reactivex.disposables.Disposable
import nz.co.chrisdrake.tv.domain.interactor.GetWhatsOnNow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class MainActionCreator @Inject constructor(
    private val store: MainStore,
    private val listingsUseCase: GetWhatsOnNow
) {
  private var listingsDisposable: Disposable? = null

  fun fetchListings() {
    listingsDisposable?.dispose()
    listingsDisposable = listingsUseCase.buildObservable().subscribe(store)
  }
}