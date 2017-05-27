package nz.co.chrisdrake.tv.data.repository

import com.jakewharton.rx.ReplayingShare
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import nz.co.chrisdrake.tv.data.database.ChannelDataService
import nz.co.chrisdrake.tv.data.api.ApiService
import nz.co.chrisdrake.tv.data.api.model.OpgResponse
import nz.co.chrisdrake.tv.data.api.model.mapper.transform
import nz.co.chrisdrake.tv.data.database.ChannelData
import nz.co.chrisdrake.tv.domain.model.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ListingsRepository @Inject constructor(
    apiService: ApiService,
    dataService: ChannelDataService
) {
  private val channelPreferences by lazy {
    dataService.channels
        .filter { it.isNotEmpty() }
        .firstOrError()
        .map { it.associateBy { it.channelId } }
        .toObservable()
  }

  val listings: Observable<List<Channel>> = apiService.listings()
      .doOnSuccess { (channels) -> dataService.insertOrUpdate(channels.data) }
      .map(OpgResponse::transform)
      .toObservable()
      .compose(ReplayingShare.instance())
      .withLatestFrom(channelPreferences, filterAndSortChannels())

  private fun filterAndSortChannels() =
      BiFunction<List<Channel>, Map<Int, ChannelData>, List<Channel>> { channels, preferences ->
        channels
            .filter { preferences.getValue(it.id).isVisible }
            .sortedWith(Comparator { (id), (id2) ->
              preferences.getValue(id).listOrder - preferences.getValue(id2).listOrder
            })
      }
}