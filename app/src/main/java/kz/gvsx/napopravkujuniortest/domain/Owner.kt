package kz.gvsx.napopravkujuniortest.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val id: Int,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)
