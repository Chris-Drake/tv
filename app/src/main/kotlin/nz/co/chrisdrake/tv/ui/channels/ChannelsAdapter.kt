package nz.co.chrisdrake.tv.ui.channels

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.data.model.ChannelRow

class ChannelsAdapter(
    val toggleVisibilityListener: (ChannelRow) -> Unit,
    val dragStartListener: (viewHolder: ChannelsAdapter.ViewHolder) -> Unit
) : RecyclerView.Adapter<ChannelsAdapter.ViewHolder>() {

  var channels: List<ChannelRow> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  init {
    setHasStableIds(true)
  }

  override fun getItemCount() = channels.size

  override fun getItemId(position: Int) = channels[position].channelId

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_channel_settings, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    with(holder) {
      val channel = channels[position]
      name.text = channel.name
      
      if (channel.isVisible) {
        visible.setImageResource(R.drawable.ic_visible)
        visible.contentDescription = "Toggle visibility off"
      } else {
        visible.setImageResource(R.drawable.ic_hidden)
        visible.contentDescription = "Toggle visibility on"
      }

      visible.setOnClickListener { toggleVisibilityListener.invoke(channels[position]) }

      reorder.setOnTouchListener { _, event ->
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
          dragStartListener.invoke(this)
        }
        false
      }
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.reorder) lateinit var reorder: View
    @BindView(R.id.name) lateinit var name: TextView
    @BindView(R.id.visible) lateinit var visible: ImageView

    init {
      ButterKnife.bind(this, itemView)
    }
  }
}