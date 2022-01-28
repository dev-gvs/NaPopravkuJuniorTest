package kz.gvsx.napopravkujuniortest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.gvsx.napopravkujuniortest.data.GitHubService
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    gitHubService: GitHubService
) : ViewModel() {
    val repositories = liveData {
        emit(gitHubService.listPublicRepositories())
    }
}