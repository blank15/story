package com.blank.domain.repository

import androidx.paging.PagingData
import com.blank.domain.model.Resource
import com.blank.model.database.StoryModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoriesRepository {
    fun getStories(): Flow<PagingData<StoryModel>>
    fun getStoriesForMap(): Flow<Resource<List<StoryModel>>>
    fun logout()
    fun uploadStory(desc: String, image: File): Flow<Resource<Any>>
}