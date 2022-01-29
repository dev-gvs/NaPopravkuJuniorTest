package kz.gvsx.napopravkujuniortest.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kz.gvsx.napopravkujuniortest.data.GitHubService
import kz.gvsx.napopravkujuniortest.domain.Commit
import kz.gvsx.napopravkujuniortest.domain.Repository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    gitHubService: GitHubService
) : ViewModel() {
    private val _selectedRepository =
        MutableStateFlow(savedStateHandle.get<Repository>(DetailsFragment.REPOSITORY_KEY))
    val selectedRepository = _selectedRepository.asStateFlow()

    private val _selectedRepositoryLastCommit = MutableStateFlow<Commit?>(null)
    val selectedRepositoryLastCommit = _selectedRepositoryLastCommit.asStateFlow()

    init {
        selectedRepository
            .filterNotNull()
            .flatMapLatest { repo ->
                flow {
                    val commits = gitHubService.listRepositoryCommits(repo.fullName)
                    emit(commits.first())
                }
            }
            .onEach { commit ->
                _selectedRepositoryLastCommit.update { commit }
            }
            .launchIn(viewModelScope)
    }
}