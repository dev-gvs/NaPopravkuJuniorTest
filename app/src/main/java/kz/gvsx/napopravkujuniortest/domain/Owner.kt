package kz.gvsx.napopravkujuniortest.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Owner(
    val id: Int,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String
) : Parcelable
