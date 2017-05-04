package nz.co.chrisdrake.tv.ui.listings

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
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
      recyclerView.adapter = ListingsAdapter(onItemChangeTransitionStartListener = {
        TransitionManager.beginDelayedTransition(itemView.parent as ViewGroup)
      }).apply { listings = channel.listings }
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.logo) lateinit var logo: ImageView
    @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

    init {
      ButterKnife.bind(this, itemView)
      recyclerView.layoutManager = LinearLayoutManager(itemView.context).apply {
        initialPrefetchItemCount = 3
      }
      recyclerView.itemAnimator = null
    }
  }
}