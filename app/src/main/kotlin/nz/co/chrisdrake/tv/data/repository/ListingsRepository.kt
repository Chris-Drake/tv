package nz.co.chrisdrake.tv.data.repository

import com.jakewharton.rx.ReplayingShare
import io.reactivex.Observable
import nz.co.chrisdrake.tv.data.ChannelDataService
import nz.co.chrisdrake.tv.data.api.ApiService
import nz.co.chrisdrake.tv.data.api.model.OpgResponse
import nz.co.chrisdrake.tv.data.api.model.mapper.transform
import nz.co.chrisdrake.tv.domain.model.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ListingsRepository @Inject constructor(
    apiService: ApiService,
    dataService: ChannelDataService
) {
  val listings: Observable<List<Channel>> = apiService.listings()
      .doOnSuccess { (channels) -> dataService.insertOrUpdate(channels.data) }
      .map(OpgResponse::transform)
      .toObservable()
      .compose(ReplayingShare.instance())
}