package nz.co.chrisdrake.tv.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import nz.co.chrisdrake.tv.R
import nz.co.chrisdrake.tv.ui.channels.ChannelsActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var store: MainStore
  @Inject lateinit var actionCreator: MainActionCreator
  @Inject lateinit var picasso: Picasso

  @BindView(R.id.container) lateinit var viewContainer: ViewGroup
  @BindView(R.id.refresh_layout) lateinit var swipeRefreshLayout: SwipeRefreshLayout
  @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView

  private val disposables = CompositeDisposable()
  private lateinit var adapter: ChannelAdapter
  private var filterItem: MenuItem? = null
  private var errorSnackbar: Snackbar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    ButterKnife.bind(this)

    adapter = ChannelAdapter(picasso)
    swipeRefreshLayout.setColorSchemeResources(R.color.primary)
    swipeRefreshLayout.setOnRefreshListener { attemptRefresh() }
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()

    disposables += store.subscribe {
      setUiModel(it)
    }

    attemptRefresh()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    filterItem = menu.findItem(R.id.filter)
    filterItem!!.isVisible = adapter.channels.isNotEmpty()
    return true
  }

  override fun onStop() {
    super.onStop()
    disposables.clear()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item == filterItem) {
      startActivity(Intent(this, ChannelsActivity::class.java))
      return true
    }

    return super.onOptionsItemSelected(item)
  }

  private fun setUiModel(model: MainUiModel) {
    Timber.d("$model")

    swipeRefreshLayout.isRefreshing = model.inProgress
    adapter.channels = model.listings?.channels ?: emptyList()
    filterItem?.isVisible = adapter.channels.isNotEmpty()

    if (model.errorMessage != null) {
      errorSnackbar = Snackbar.make(viewContainer, model.errorMessage, Snackbar.LENGTH_INDEFINITE)
          .setAction(R.string.error_retry) { attemptRefresh() }
      errorSnackbar!!.show()
    } else {
      errorSnackbar?.dismiss()
    }
  }

  private fun attemptRefresh() {
    actionCreator.fetchListings()
  }
}
