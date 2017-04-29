package nz.co.chrisdrake.tv.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.domain.model.Channel

class ChannelAdapter(
    private val picasso: Picasso
) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

  var channels: List<Channel> = emptyList()
    set(value) {
      val changed = value != field
      field = value
      if (changed) notifyDataSetChanged()
    }

  init {
    setHasStableIds(true)
  }

  override fun getItemCount() = channels.size

  override fun getItemId(position: Int) = channels[position].id.toLong()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_channel, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    with(holder) {
      val channel = channels[position]
      logo.contentDescription = channel.name
      picasso.load(channel.logoImageUrl).fit().into(logo)
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.logo) lateinit var logo: ImageView

    init {
      ButterKnife.bind(this, itemView)
    }
  }
}