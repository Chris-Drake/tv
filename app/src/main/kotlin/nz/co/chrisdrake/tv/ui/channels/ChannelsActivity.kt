package nz.co.chrisdrake.tv.ui.channels

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxrelay2.PublishRelay
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.data.database.ChannelDataService
import javax.inject.Inject

class ChannelsActivity : AppCompatActivity() {

  @Inject lateinit var dataService: ChannelDataService

  @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

  private val adapter: ChannelsAdapter
  private val disposables = CompositeDisposable()
  private val changeRequestRelay = PublishRelay.create<ChannelChange>()

  init {
    adapter = ChannelsAdapter(
        toggleVisibilityListener = {
          changeRequestRelay.accept(ChannelChange.ToggleVisibility(it.channelId))
        },
        dragStartListener = { itemTouchHelper.startDrag(it) })
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_channels)
    ButterKnife.bind(this)

    itemTouchHelper.attachToRecyclerView(recyclerView)
    recyclerView.setHasFixedSize(true)
    recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    disposables += dataService.channels
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { adapter.channels = it }

    disposables += changeRequestRelay
        .observeOn(Schedulers.io())
        .subscribe(this::onChannelChangeRequested)
  }

  override fun onStop() {
    super.onStop()
    disposables.clear()
  }

  private fun onChannelChangeRequested(change: ChannelChange) {
    when (change) {
      is ChannelChange.ToggleVisibility -> dataService.toggleVisibility(change.channelId)
      is ChannelChange.Move -> dataService.moveItem(change.channelId, change.from, change.to)
    }
  }

  private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
      return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
      val fromPosition = viewHolder.adapterPosition
      val toPosition = target.adapterPosition
      val channel = adapter.channels[fromPosition]
      changeRequestRelay.accept(ChannelChange.Move(channel.channelId, fromPosition, toPosition))
      return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
  })

  private sealed class ChannelChange {
    data class ToggleVisibility(val channelId: Int) : ChannelChange()
    data class Move(val channelId: Int, val from: Int, val to: Int) : ChannelChange()
  }
}