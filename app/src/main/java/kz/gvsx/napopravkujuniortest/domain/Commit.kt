package kz.gvsx.napopravkujuniortest.domain

import kotlinx.serialization.Serializable

@Serializable
data class Commit(
    val sha: String,
    private val commit: CommitInfo,
    private val parents: List<Parent>
) {
    val message get() = commit.message
    val authorName get() = commit.author.name
    val date get() = commit.author.date
    val parentHashes get() = parents.map { it.sha }
}

@Serializable
data class CommitInfo(
    val author: Author,
    val message: String
)

@Serializable
data class Author(
    val name: String,
    val date: String
)

@Serializable
data class Parent(
    val sha: String
)