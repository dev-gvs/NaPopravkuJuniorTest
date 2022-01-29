package kz.gvsx.napopravkujuniortest.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.gvsx.napopravkujuniortest.data.GitHubService
import kz.gvsx.napopravkujuniortest.data.RepositoriesPagingSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    gitHubService: GitHubService
) : ViewModel() {
    val repositoriesPagingFlow = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false)
    ) { RepositoriesPagingSource(gitHubService) }
        .flow
        .cachedIn(viewModelScope)
}