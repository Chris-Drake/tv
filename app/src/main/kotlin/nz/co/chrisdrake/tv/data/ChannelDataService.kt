package nz.co.chrisdrake.tv.data

import com.jakewharton.rx.ReplayingShare
import com.squareup.sqlbrite.BriteDatabase
import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.Observable
import nz.co.chrisdrake.tv.data.ChannelModel.InsertOrIgnore
import nz.co.chrisdrake.tv.data.ChannelModel.UpdateName
import nz.co.chrisdrake.tv.data.api.model.Channel
import nz.co.chrisdrake.tv.data.api.model.ChannelDetails
import nz.co.chrisdrake.tv.data.model.ChannelRow
import nz.co.chrisdrake.tv.util.bindAndExecute
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ChannelDataService @Inject constructor(
    private val briteDatabase: BriteDatabase
) {
  private val writableDatabase by lazy { briteDatabase.writableDatabase }

  val channels: Observable<List<ChannelRow>> by lazy {
    val selectAll = ChannelRow.FACTORY.selectAll()

    RxJavaInterop.toV2Observable(
        briteDatabase
            .createQuery(selectAll.tables, selectAll.statement)
            .mapToList(ChannelRow.FACTORY.selectAllMapper()::map))
        .compose(ReplayingShare.instance())
  }

  fun insertOrUpdate(channels: List<Channel>) {
    val insertOrIgnore = InsertOrIgnore(writableDatabase)
    val updateName by lazy { UpdateName(writableDatabase) }

    briteDatabase.newTransaction().use { transaction ->
      channels.forEachIndexed { index, (_, details, _) ->
        val inserted = briteDatabase.bindAndExecute(insertOrIgnore) { bind(details, index) } != -1L
        if (!inserted) {
          briteDatabase.bindAndExecute(updateName) { bind(details) }
        }
      }

      transaction.markSuccessful();
    }
  }

  private fun InsertOrIgnore.bind(details: ChannelDetails, listOrder: Int) = with(details) {
    bind(channelId.toLong(), name, !isRegional, listOrder.toLong())
  }

  private fun UpdateName.bind(details: ChannelDetails) = with(details) {
    bind(name, channelId.toLong())
  }
}