package nz.co.chrisdrake.tv.data.api.model.mapper

import nz.co.chrisdrake.tv.data.api.model.OpgResponse
import nz.co.chrisdrake.tv.domain.model.Channel
import nz.co.chrisdrake.tv.domain.model.Program
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

fun OpgResponse.transform(): List<Channel> {
  return channels.data.map { (logoImagePath, details, programs) ->
    Channel(
        id = details.channelId,
        logoImageUrl = "http://www.freeviewnz.tv$logoImagePath",
        name = details.name,
        isRegional = details.isRegional,
        listings = programs.data.map {
          with(it) {
            Program(
                id = hashCode(),
                title = title,
                episodeTitle = episodeTitle,
                synopsis = synopsis,
                classification = classification,
                genre = genre,
                startTime = ZonedDateTime.parse(startTime),
                endTime = ZonedDateTime.parse(endTime),
                duration = Duration.ofMillis(durationMilliseconds))
          }
        })
  }
}