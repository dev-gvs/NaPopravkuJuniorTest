package kz.gvsx.napopravkujuniortest.data

import kz.gvsx.napopravkujuniortest.domain.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("/repositories")
    suspend fun listPublicRepositories(@Query("since") since: Int): List<Repository>
}