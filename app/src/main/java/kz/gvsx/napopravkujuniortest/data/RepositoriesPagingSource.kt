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
            // Last item id in the returned response is going to be the next offset.
            val nextKey = response.last().id

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
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