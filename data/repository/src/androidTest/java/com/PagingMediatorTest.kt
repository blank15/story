package com

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blank.local.StoryDatabase
import com.blank.model.database.StoryModel
import com.blank.remote.datasource.StoriesDataSource
import com.blank.repository.util.PagingMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class PagingMediatorTest {

    val mockApi = FakeStoriesService()
    lateinit var storiesDataSource: StoriesDataSource
    lateinit var pagingMediator: PagingMediator
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @Before
    fun setUp() {
        storiesDataSource = StoriesDataSource(mockApi)
        pagingMediator = PagingMediator(storiesDataSource, mockDb)

    }

    @After
    fun cleanUp() {
        mockDb.clearAllTables()
    }

    @Test
    fun whenLoadReturnSuccessIfDataIsPresent() = runTest {
        val pagingState = PagingState<Int, StoryModel>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = pagingMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}