package nz.co.chrisdrake.tv.domain.model

data class ChannelListings(val channels: List<Channel>) {
  override fun toString(): String {
    return "ChannelListings(channels=${this.channels.size})"
  }
}