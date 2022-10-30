package com.blank.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull


@Entity(tableName = "story")
@Parcelize
data class StoryModel(
    @PrimaryKey
    @NotNull
    @SerializedName("id")
    val id: String,
    val name: String?,
    val photoUrl: String?,
    val description: String?,
    val lat: Double?,
    val lon: Double?
) : Parcelable