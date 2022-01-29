package kz.gvsx.napopravkujuniortest.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.gvsx.napopravkujuniortest.data.GitHubService
import kz.gvsx.napopravkujuniortest.domain.Repository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    gitHubService: GitHubService
) : ViewModel() {
    val selectedRepository = savedStateHandle.get<Repository>(DetailsFragment.REPOSITORY_KEY)

    init {
        selectedRepository?.let { repository ->
            viewModelScope.launch {
                val commits = gitHubService.listRepositoryCommits(repository.fullName)
                Timber.d(commits.first().toString())
            }
        }
    }
}