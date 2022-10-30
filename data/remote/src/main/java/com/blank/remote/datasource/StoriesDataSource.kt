package com.blank.remote.datasource

import com.blank.model.BaseResponse
import com.blank.model.story.StoryItemResponse
import com.blank.remote.api.StoriesService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class StoriesDataSource(private val storiesService: StoriesService) {

    suspend fun getStories(page: Int, size: Int, location: Int?): Response<StoryItemResponse> =
        storiesService.getStory(page, size, location ?: 0)

    suspend fun uploadStory(desc: String, file: File): Response<BaseResponse> {
        val description = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo", file.name, requestImageFile
        )
        return storiesService.uploadStory(description, imageMultipart)
    }
}