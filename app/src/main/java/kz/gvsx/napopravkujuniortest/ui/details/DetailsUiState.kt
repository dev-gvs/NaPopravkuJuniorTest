package kz.gvsx.napopravkujuniortest.ui.details

import kz.gvsx.napopravkujuniortest.domain.Commit
import kz.gvsx.napopravkujuniortest.domain.Repository

data class DetailsUiState(
    val repository: Repository,
    val lastCommit: Commit?,
    val isFetchingLastCommit: Boolean = false,
    val hasErrors: Boolean = false,
)