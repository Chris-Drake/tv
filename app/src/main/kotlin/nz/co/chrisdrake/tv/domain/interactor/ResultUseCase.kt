package nz.co.chrisdrake.tv.domain.interactor

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import nz.co.chrisdrake.tv.domain.model.Result

interface ResultUseCase<T> {
  fun buildObservable(): Observable<Result<T>>

  class Transformer<T> : ObservableTransformer<T, Result<T>> {
    @Suppress("USELESS_CAST")
    override fun apply(upstream: Observable<T>): ObservableSource<Result<T>> {
      return upstream
          .map { Result.Success(it) as Result<T> }
          .onErrorReturn { Result.Failure(it) }
          .startWith(Result.InProgress());
    }
  }
}