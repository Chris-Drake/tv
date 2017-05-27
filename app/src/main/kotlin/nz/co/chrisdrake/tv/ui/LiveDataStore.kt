package nz.co.chrisdrake.tv.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class LiveDataStore<in Action, State>(
    private val reducer: (State, Action) -> State, initialState: State
) {
  private val state = MutableLiveData<State>().apply { value = initialState }

  fun data(): LiveData<State> = state

  fun accept(action: Action) {
    val oldState = state.value!!
    val newState = reducer.invoke(oldState, action)
    if (newState != oldState) {
      state.value = newState
    }
  }
}
