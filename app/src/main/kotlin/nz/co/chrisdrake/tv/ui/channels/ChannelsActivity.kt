package nz.co.chrisdrake.tv.ui.channels

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import butterknife.BindView
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.data.ChannelDataService
import javax.inject.Inject

class ChannelsActivity : AppCompatActivity() {

  @Inject lateinit var dataService: ChannelDataService

  @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

  private val adapter: ChannelsAdapter = ChannelsAdapter(dragStartCallback = {
    itemTouchHelper.startDrag(it)
  })
  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_channels)
    ButterKnife.bind(this)

    itemTouchHelper.attachToRecyclerView(recyclerView)
    recyclerView.setHasFixedSize(true)
    recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    recyclerView.adapter = adapter

    disposables += dataService.channels
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { adapter.channels = it }
  }

  private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
      return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
      val fromPosition = viewHolder.adapterPosition
      val toPosition = target.adapterPosition
      val channel = adapter.channels[fromPosition]
      dataService.moveItem(channel.channelId, fromPosition, toPosition)
      return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
  })

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }
}