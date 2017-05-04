package nz.co.chrisdrake.tv.ui.listings

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

class ListingsAdapter(
    private val onItemChangeTransitionStartListener: () -> Unit
) : RecyclerView.Adapter<ListingsAdapter.ViewHolder>() {

  private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

  var listings: List<Program> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  private var expandedSelection = RecyclerView.NO_POSITION

  init {
    setHasStableIds(true)
  }

  override fun getItemCount() = listings.size

  override fun getItemId(position: Int) = listings[position].id.toLong()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_program, parent, false)
    return ViewHolder(view, onClickListener = { _, position -> onItemClick(position) });
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    with(holder) {
      val listing = listings[position]
      time.text = listing.startTime.format(timeFormatter)
      title.text = listing.title
      description.text = listing.synopsis
      description.visibility = if (expandedSelection == position) View.VISIBLE else View.GONE
    }
  }

  private fun onItemClick(position: Int) {
    val previousSelection = expandedSelection;
    expandedSelection = if (position == expandedSelection) RecyclerView.NO_POSITION else position
    onItemChangeTransitionStartListener.invoke()

    if (previousSelection != RecyclerView.NO_POSITION) {
      notifyItemChanged(previousSelection)
    }
    notifyItemChanged(position)
  }

  class ViewHolder(
      itemView: View, onClickListener: (View, Int) -> Unit
  ) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.time) lateinit var time: TextView
    @BindView(R.id.title) lateinit var title: TextView
    @BindView(R.id.description) lateinit var description: TextView

    init {
      ButterKnife.bind(this, itemView)
      itemView.setOnClickListener { onClickListener.invoke(itemView, adapterPosition) }
    }
  }
}