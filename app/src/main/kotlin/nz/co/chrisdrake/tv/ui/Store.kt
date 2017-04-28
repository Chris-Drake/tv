package nz.co.chrisdrake.tv.ui

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

abstract class Store<in Action, out State>(
    initialState: State,
    reducer: (State, Action) -> State
) : (Action) -> Unit {

  private val state: BehaviorRelay<State> = BehaviorRelay.createDefault(initialState)
  private val actions: PublishRelay<Action> = PublishRelay.create()

  init {
    actions
        .scan(initialState, reducer)
        .distinctUntilChanged()
        .subscribe(state)
  }

  fun subscribe(consumer: (State) -> Unit): Disposable {
    return state
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(consumer)
  }

  override fun invoke(action: Action) {
    actions.accept(action)
  }
}
