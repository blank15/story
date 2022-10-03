package com.blank.domain.repository

import com.blank.domain.model.Resource
import com.blank.model.story.StoryResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StoriesRepository {
    fun getStories(): Flow<Resource<List<StoryResult>>>
    fun logout()
    fun uploadStory(desc: String, image: File): Flow<Resource<Any>>
}