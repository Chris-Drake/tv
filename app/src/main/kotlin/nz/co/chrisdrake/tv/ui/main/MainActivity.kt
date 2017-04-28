package nz.co.chrisdrake.tv.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var store: MainStore

  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    disposables += store.subscribe {
      setUiModel(it)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  private fun setUiModel(uiModel: MainUiModel) {
    Timber.d("$uiModel")

    // TODO:
  }
}
