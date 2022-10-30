package com.blank.model.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryResult(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
) : Parcelable