package com.blank.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import com.blank.local.StoryDatabase
import com.blank.model.BaseResponse
import com.blank.model.database.StoryModel
import com.blank.model.story.StoryItemResponse
import com.blank.remote.datasource.StoriesDataSource
import com.blank.repository.util.NetworkHandling
import com.blank.repository.util.PagingMediator
import com.blank.repository.util.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

class StoriesRepositoryImpl(
    private val storiesDataSource: StoriesDataSource,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val storyDatabase: StoryDatabase
) : StoriesRepository {
    override fun getStories(): Flow<PagingData<StoryModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                initialLoadSize = 8,
                pageSize = 4
            ),
            remoteMediator = PagingMediator(storiesDataSource, storyDatabase),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).flow
    }

    override fun getStoriesForMap(): Flow<Resource<List<StoryModel>>> {
        return object : NetworkHandling<List<StoryModel>, StoryItemResponse>() {
            override fun processResponse(response: StoryItemResponse?): List<StoryModel>? {
                return response?.listStory?.map { result ->
                    StoryModel(
                        id = result.id,
                        name = result.name,
                        photoUrl = result.photoUrl,
                        description = result.description,
                        lat = result.lat,
                        lon = result.lon,
                    )
                }
            }

            override suspend fun createCallAsync(): Response<StoryItemResponse> {
                return storiesDataSource.getStories(1, 10, 1)
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