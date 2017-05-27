package nz.co.chrisdrake.tv.data.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "channel") data class ChannelData(
    @PrimaryKey val channelId: Int,
    val name: String,
    @ColumnInfo(name = "visible") val isVisible: Boolean,
    val listOrder: Int
)