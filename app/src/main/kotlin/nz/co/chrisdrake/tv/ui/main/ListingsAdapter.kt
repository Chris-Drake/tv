package nz.co.chrisdrake.tv.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.domain.model.Program
import org.threeten.bp.format.DateTimeFormatter

class ListingsAdapter : RecyclerView.Adapter<ListingsAdapter.ViewHolder>() {

  private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

  var listings: List<Program> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  init {
    setHasStableIds(true)
  }

  override fun getItemCount() = listings.size

  override fun getItemId(position: Int) = listings[position].id.toLong()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_program, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    with(holder) {
      val listing = listings[position]
      time.text = listing.startTime.format(timeFormatter)
      title.text = listing.title
      description.text = listing.synopsis
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.time) lateinit var time: TextView
    @BindView(R.id.title) lateinit var title: TextView
    @BindView(R.id.description) lateinit var description: TextView

    init {
      ButterKnife.bind(this, itemView)
    }
  }
}