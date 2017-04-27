package nz.co.chrisdrake.tv.domain.model

import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

data class Program(
    val title: String,
    val episodeTitle: String?,
    val synopsis: String?,
    val classification: String?,
    val genre: String?,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime,
    val duration: Duration
)