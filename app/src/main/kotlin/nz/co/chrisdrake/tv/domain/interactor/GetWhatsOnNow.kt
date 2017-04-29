package nz.co.chrisdrake.tv.domain.interactor

import io.reactivex.Observable
import nz.co.chrisdrake.tv.data.repository.ListingsRepository
import nz.co.chrisdrake.tv.domain.model.ChannelListings
import nz.co.chrisdrake.tv.domain.model.Result
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class GetWhatsOnNow @Inject constructor(
    private val listingsRepository: ListingsRepository
) : ResultUseCase<ChannelListings> {

  override fun buildObservable(): Observable<Result<ChannelListings>> {
    return listingsRepository.listings
        .flatMap {
          val now = ZonedDateTime.now()
          Observable.fromIterable(it)
              .filter { !it.isRegional }
              .map { it.withListingsAfter(now) }
              .map { it.copy(listings = it.listings.take(3)) }
              .toList()
              .toObservable()
        }
        .map(::ChannelListings)
        .compose(ResultUseCase.Transformer())
  }
}