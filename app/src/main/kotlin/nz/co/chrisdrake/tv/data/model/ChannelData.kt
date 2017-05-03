package nz.co.chrisdrake.tv.data.model

import nz.co.chrisdrake.tv.data.ChannelModel

data class ChannelData(
    private val _id: Long,
    val channelId: Long,
    val name: String,
    val isVisible: Boolean,
    val listOrder: Long
) : ChannelModel {

  companion object {
    val FACTORY: ChannelModel.Factory<ChannelData> = ChannelModel.Factory(ChannelModel.Creator { _id, channelId, name, visible, listOrder ->
      ChannelData(_id = _id, channelId = channelId, name = name, isVisible = visible, listOrder = listOrder)
    })
  }

  override fun _id() = _id
  override fun channelId() = channelId
  override fun name() = name
  override fun visible() = isVisible
  override fun listOrder() = listOrder
}