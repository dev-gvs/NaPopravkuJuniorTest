package kz.gvsx.napopravkujuniortest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kz.gvsx.napopravkujuniortest.domain.Repository
import timber.log.Timber

class RepositoriesPagingSource(
    private val service: GitHubService
) : PagingSource<Int, Repository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {
            val since = params.key ?: 0
            Timber.d("load repositories since $since id")
            val response = service.listPublicRepositories(since)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = response.last().id
            )
        } catch (t: Throwable) {
            Timber.e(t)
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
}