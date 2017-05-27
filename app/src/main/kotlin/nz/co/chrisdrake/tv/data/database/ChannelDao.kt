package nz.co.chrisdrake.tv.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao interface ChannelDao {
  @Query("SELECT * FROM channel ORDER BY listOrder ASC")
  fun selectAll(): Flowable<List<ChannelData>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertOrIgnore(channel: ChannelData): Long

  @Query("UPDATE channel SET name = :p0 WHERE channelId = :p1")
  fun updateName(name: String, channelId: Int): Int

  @Query("UPDATE channel SET visible = NOT visible WHERE channelId = :p0")
  fun toggleVisibility(channelId: Int): Int

  @Query("UPDATE channel SET listOrder = listOrder + :p0 WHERE listOrder BETWEEN :p1 AND :p2")
  fun incrementListOrders(increment: Int, start: Int, end: Int): Int

  @Query("UPDATE channel SET listOrder = :p0 WHERE channelId = :p1")
  fun updateListOrder(listOrder: Int, channelId: Int): Int
}
