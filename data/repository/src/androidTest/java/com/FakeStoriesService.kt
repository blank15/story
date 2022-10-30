package com

import com.blank.model.BaseResponse
import com.blank.model.story.StoryItemResponse
import com.blank.model.story.StoryResult
import com.blank.remote.api.StoriesService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class FakeStoriesService : StoriesService {
    override suspend fun getStory(
        page: Int,
        size: Int,
        location: Int?
    ): Response<StoryItemResponse> {
        val storyResult: MutableList<StoryResult> = arrayListOf()
        for (i in 0..100) {
            storyResult.add(
                StoryResult(
                    id = i.toString(),
                    name = "story$i",
                    photoUrl = "url $i",
                    description = "desc $i",
                    lat = i.toDouble(),
                    lon = i.toDouble()
                )
            )
        }
        return Response.success(StoryItemResponse(false, "success", storyResult))
    }

    override suspend fun uploadStory(
        data: RequestBody,
        file: MultipartBody.Part
    ): Response<BaseResponse> {
        TODO("Not yet implemented")
    }
}