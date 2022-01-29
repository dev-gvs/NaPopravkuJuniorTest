package kz.gvsx.napopravkujuniortest.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val selectedRepository = savedStateHandle.get<Repository>(DetailsFragment.REPOSITORY_KEY)

}