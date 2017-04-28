package nz.co.chrisdrake.tv.ui.main

import nz.co.chrisdrake.tv.domain.model.ChannelListings
import nz.co.chrisdrake.tv.domain.model.Result
import nz.co.chrisdrake.tv.ui.Store
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class MainStore @Inject constructor() : Store<Result<ChannelListings>, MainUiModel>(
    initialState = MainUiModel(),
    reducer = MainUiModel::reduce
)