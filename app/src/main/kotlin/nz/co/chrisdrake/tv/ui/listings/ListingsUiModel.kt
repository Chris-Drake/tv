package nz.co.chrisdrake.tv.ui.listings

import nz.co.chrisdrake.tv.domain.model.ChannelListings
import nz.co.chrisdrake.tv.domain.model.Result
import timber.log.Timber

data class ListingsUiModel(
    val inProgress: Boolean = false,
    val listings: ChannelListings? = null,
    val errorMessage: String? = null
) {
  fun reduce(action: Result<ChannelListings>): ListingsUiModel {
    Timber.d("$action")

    return when (action) {
      is Result.Success -> ListingsUiModel(listings = action.value)
      is Result.Failure -> this.copy(inProgress = false, errorMessage = action.errorMessage())
      is Result.InProgress -> this.copy(inProgress = true, errorMessage = null)
    }
  }
}