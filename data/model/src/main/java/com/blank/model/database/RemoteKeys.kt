package com.blank.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    @field:SerializedName("prevKey")
    val prevKey: Int?,
    @field:SerializedName("nextKey")
    val nextKey: Int?
)