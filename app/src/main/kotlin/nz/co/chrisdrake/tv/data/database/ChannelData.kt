package nz.co.chrisdrake.tv.data.database

import nz.co.chrisdrake.tv.data.database.ChannelModel.Factory

data class ChannelData(
    private val _id: Long,
    val channelId: Long,
    val name: String,
    val isVisible: Boolean,
    val listOrder: Long
) : ChannelModel {

  companion object {
    val FACTORY: Factory<ChannelData> = Factory(::ChannelData)
  }

  override fun _id() = _id
  override fun channelId() = channelId
  override fun name() = name
  override fun visible() = isVisible
  override fun listOrder() = listOrder
}