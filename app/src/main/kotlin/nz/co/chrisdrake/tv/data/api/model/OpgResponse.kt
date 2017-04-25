package nz.co.chrisdrake.tv.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root data class OpgResponse(
    @field:Element(name = "Channels")
    @param:Element(name = "Channels")
    val channels: Channels
)

@Root data class Channels(
    @field:ElementList(inline = true)
    @param:ElementList(inline = true)
    val data: List<Channel>
)

@Root(name = "OpgChannelItem") data class Channel(
    @field:Element(name = "ChannelLogoImageUrl")
    @param:Element(name = "ChannelLogoImageUrl")
    val logoImagePath: String,
    @field:Element(name = "Channel")
    @param:Element(name = "Channel")
    val details: ChannelDetails,
    @field:Element(name = "Programs")
    @param:Element(name = "Programs")
    val programs: Programs
)

@Root data class ChannelDetails(
    @field:Attribute(name = "ChannelId")
    @param:Attribute(name = "ChannelId")
    val channelId: Int,
    @field:Attribute(name = "FreeviewChannelNumber")
    @param:Attribute(name = "FreeviewChannelNumber")
    val channelNumber: Int,
    @field:Attribute(name = "Name")
    @param:Attribute(name = "Name")
    val name: String,
    @field:Attribute(name = "IsRegional")
    @param:Attribute(name = "IsRegional")
    val isRegional: Boolean
)

@Root data class Programs(
    @field:ElementList(name = "Programs")
    @param:ElementList(name = "Programs")
    val data: List<Program>
)

@Root data class Program(
    @field:Element(name = "Title")
    @param:Element(name = "Title")
    val title: String,
    @field:Element(name = "EpisodeTitle", required = false)
    @param:Element(name = "EpisodeTitle", required = false)
    val episodeTitle: String?,
    @field:Element(name = "Synopsis", required = false)
    @param:Element(name = "Synopsis", required = false)
    val synopsis: String?,
    @field:Element(name = "Classification", required = false)
    @param:Element(name = "Classification", required = false)
    val classification: String?,
    @field:Element(name = "Genre", required = false)
    @param:Element(name = "Genre", required = false)
    val genre: String?,
    @field:Element(name = "StartTime")
    @param:Element(name = "StartTime")
    val startTime: String,
    @field:Element(name = "EndTime")
    @param:Element(name = "EndTime")
    val endTime: String,
    @field:Element(name = "DurationMilliseconds")
    @param:Element(name = "DurationMilliseconds")
    val durationMilliseconds: Long
)