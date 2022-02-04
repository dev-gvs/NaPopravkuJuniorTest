package kz.gvsx.napopravkujuniortest.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.gvsx.napopravkujuniortest.data.GitHubService
import kz.gvsx.napopravkujuniortest.domain.Repository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gitHubService: GitHubService
) : ViewModel() {

    val selectedRepository =
        requireNotNull(savedStateHandle.get<Repository>(DetailsFragment.REPOSITORY_KEY)) {
            "repository shouldn't be null"
        }

    private val _uiState = MutableStateFlow(
        DetailsUiState(
            repository = selectedRepository,
            lastCommit = null,
            isFetchingLastCommit = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        fetchLastCommit()
    }

    fun fetchLastCommit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingLastCommit = true, hasErrors = false) }

            try {
                // In more complex application this logic should be in the Repository or a UseCase.
                val commits = gitHubService.listRepositoryCommits(selectedRepository.fullName)
                // First list element is the last commit of the repository.
                val lastCommit = commits.first()

                _uiState.update {
                    it.copy(lastCommit = lastCommit)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(isFetchingLastCommit = false, hasErrors = true)
                }
            }
        }
    }
}