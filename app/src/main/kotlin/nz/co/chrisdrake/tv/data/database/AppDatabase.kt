package nz.co.chrisdrake.tv.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(
    entities = arrayOf(ChannelData::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun channelDao(): ChannelDao
}