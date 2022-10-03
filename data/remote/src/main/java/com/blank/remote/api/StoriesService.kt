package com.blank.remote.api

import com.blank.model.BaseResponse
import com.blank.model.story.StoryItemResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StoriesService {

    @GET("stories")
    suspend fun getStory(): Response<StoryItemResponse>

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part("description") data: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<BaseResponse>
}