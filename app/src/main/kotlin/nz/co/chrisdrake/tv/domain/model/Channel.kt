package nz.co.chrisdrake.tv.domain.model

import org.threeten.bp.ZonedDateTime

data class Channel(
    val id: Int,
    val logoImageUrl: String,
    val name: String,
    val isRegional: Boolean,
    val listings: List<Program>
) {
  fun withListingsAfter(date: ZonedDateTime): Channel {
    return copy(listings = listings.filter { it.endTime.isAfter(date) })
  }
}