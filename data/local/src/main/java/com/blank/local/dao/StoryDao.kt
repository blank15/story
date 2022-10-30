package com.blank.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blank.model.database.StoryModel

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryModel>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryModel>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}