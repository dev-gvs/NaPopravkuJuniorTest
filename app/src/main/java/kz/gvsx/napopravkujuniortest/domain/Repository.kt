package kz.gvsx.napopravkujuniortest.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    @SerialName("full_name") val fullName: String,
    val owner: Owner
)
