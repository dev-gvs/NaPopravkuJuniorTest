package kz.gvsx.napopravkujuniortest.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Repository(
    val id: Int,
    @SerialName("full_name") val fullName: String,
    val owner: Owner
) : Parcelable
