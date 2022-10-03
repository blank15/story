package com.blank.model.story

import com.google.gson.annotations.SerializedName

data class StoryItemResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("listStory")
    val listStory: List<StoryResult>
)