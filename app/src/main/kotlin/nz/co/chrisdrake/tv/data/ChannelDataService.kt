package nz.co.chrisdrake.tv.data

import com.jakewharton.rx.ReplayingShare
import com.squareup.sqlbrite.BriteDatabase
import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.Observable
import nz.co.chrisdrake.tv.data.ChannelModel.*
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
  private val toggleVisibility by lazy { ToggleVisibility(writableDatabase) }
  private val incrementListOrders by lazy { IncrementListOrders(writableDatabase) }
  private val updateListOrder by lazy { UpdateListOrder(writableDatabase) }

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

  fun toggleVisibility(channelId: Long) {
    briteDatabase.bindAndExecute(toggleVisibility) {
      bind(channelId)
    }
  }

  fun moveItem(channelId: Long, fromPosition: Int, toPosition: Int) {
    briteDatabase.newTransaction().use { transaction ->
      if (toPosition > fromPosition) {
        incrementListOrders(-1, fromPosition + 1..toPosition)
      } else {
        incrementListOrders(1, toPosition..fromPosition - 1)
      }

      updateListOrder(channelId = channelId, listOrder = toPosition)

      transaction.markSuccessful()
    }
  }

  private fun incrementListOrders(amount: Int, range: IntRange) {
    briteDatabase.bindAndExecute(incrementListOrders) {
      bind(amount.toDouble(), range.first.toLong(), range.last.toLong())
    }
  }

  private fun updateListOrder(channelId: Long, listOrder: Int) {
    briteDatabase.bindAndExecute(updateListOrder) {
      bind(listOrder.toLong(), channelId)
    }
  }

  private fun InsertOrIgnore.bind(details: ChannelDetails, listOrder: Int) = with(details) {
    bind(channelId.toLong(), name, !isRegional, listOrder.toLong())
  }

  private fun UpdateName.bind(details: ChannelDetails) = with(details) {
    bind(name, channelId.toLong())
  }
}