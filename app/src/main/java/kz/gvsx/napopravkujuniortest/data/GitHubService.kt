package kz.gvsx.napopravkujuniortest.data

import kz.gvsx.napopravkujuniortest.domain.Repository
import retrofit2.http.GET

interface GitHubService {

    @GET("/repositories")
    suspend fun listPublicRepositories(): List<Repository>
}