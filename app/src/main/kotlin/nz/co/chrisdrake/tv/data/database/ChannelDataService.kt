package nz.co.chrisdrake.tv.data.database

import android.arch.persistence.room.RoomDatabase
import com.jakewharton.rx.ReplayingShare
import io.reactivex.Flowable
import nz.co.chrisdrake.tv.data.api.model.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ChannelDataService @Inject constructor(
    private val appDatabase: AppDatabase,
    private val channelDao: ChannelDao
) {
  val channels: Flowable<List<ChannelData>> by lazy {
    channelDao.selectAll().compose(ReplayingShare.instance())
  }

  fun insertOrUpdate(channels: List<Channel>) {
    appDatabase.newTransaction {
      channels.forEachIndexed { index, (_, details, _) ->
        val channelData = with(details) {
          ChannelData(
              channelId = channelId,
              name = name,
              isVisible = !isRegional && channelNumber < 50,
              listOrder = index)
        }

        val inserted = channelDao.insertOrIgnore(channelData) != -1L
        if (!inserted) {
          channelDao.updateName(details.name, details.channelId)
        }
      }

      setTransactionSuccessful()
    }
  }

  fun toggleVisibility(channelId: Int) {
    channelDao.toggleVisibility(channelId)
  }

  fun moveItem(channelId: Int, fromPosition: Int, toPosition: Int) {
    appDatabase.newTransaction {
      if (toPosition > fromPosition) {
        channelDao.incrementListOrders(-1, fromPosition + 1, toPosition)
      } else {
        channelDao.incrementListOrders(1, toPosition, fromPosition - 1)
      }

      channelDao.updateListOrder(channelId = channelId, listOrder = toPosition)

      setTransactionSuccessful()
    }
  }

  private fun RoomDatabase.newTransaction(transaction: RoomDatabase.() -> Unit) {
    beginTransaction();
    try {
      transaction()
    } finally {
      endTransaction();
    }
  }
}