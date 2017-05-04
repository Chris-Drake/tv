package nz.co.chrisdrake.tv.ui.listings

import nz.co.chrisdrake.tv.domain.model.ChannelListings
import nz.co.chrisdrake.tv.domain.model.Result
import nz.co.chrisdrake.tv.ui.Store
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ListingsStore @Inject constructor()
  : Store<Result<ChannelListings>, ListingsUiModel>(
    initialState = ListingsUiModel(),
    reducer = ListingsUiModel::reduce
)