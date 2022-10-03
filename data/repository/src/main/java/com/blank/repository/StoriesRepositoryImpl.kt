package com.blank.repository

import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import com.blank.model.BaseResponse
import com.blank.model.story.StoryItemResponse
import com.blank.model.story.StoryResult
import com.blank.remote.datasource.StoriesDataSource
import com.blank.repository.util.NetworkHandling
import com.blank.repository.util.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

class StoriesRepositoryImpl(
    private val storiesDataSource: StoriesDataSource,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : StoriesRepository {
    override fun getStories(): Flow<Resource<List<StoryResult>>> {
        return object : NetworkHandling<List<StoryResult>, StoryItemResponse>() {
            override fun processResponse(response: StoryItemResponse?): List<StoryResult>? {
                return response?.listStory
            }

            override suspend fun createCallAsync(): Response<StoryItemResponse> {
                return storiesDataSource.getStories()
            }
        }.asFlow
    }

    override fun logout() {
        sharedPreferenceHelper.clearData()
    }

    override fun uploadStory(desc: String, image: File): Flow<Resource<Any>> {
        return object : NetworkHandling<Any, BaseResponse>() {
            override fun processResponse(response: BaseResponse?): Any? {
                return null
            }

            override suspend fun createCallAsync(): Response<BaseResponse> {
                return storiesDataSource.uploadStory(desc, image)
            }
        }.asFlow
    }
}