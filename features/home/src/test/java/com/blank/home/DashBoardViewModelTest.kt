package com.blank.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.test.filters.SmallTest
import com.blank.domain.model.ErrorResponse
import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import com.blank.home.ui.adapter.RecyclerViewStory
import com.blank.home.ui.dashboard.DashboardViewModel
import com.blank.model.database.StoryModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.* // ktlint-disable no-wildcard-imports
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class DashBoardViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatchers = UnconfinedTestDispatcher()
    private lateinit var storiesRepository: StoriesRepository
    private lateinit var dashboardViewModel: DashboardViewModel
    private val storiesMapObserver = mockk<Observer<Resource<List<StoryModel>>>>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatchers)
        storiesRepository = mockk()
        dashboardViewModel = DashboardViewModel(storiesRepository).apply {
            storiesMap.observeForever(storiesMapObserver)
        }
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get stories with paging return success`() = runTest {
        // given
        val data = DummyData.generateData()
        val dataPaging = PagingSourceHelperTest.snapshot(data)

        // when
        coEvery {
            storiesRepository.getStories()
        } returns flow {
            emit(dataPaging)
        }
        dashboardViewModel.getStories()

        val differ = AsyncPagingDataDiffer(
            diffCallback = RecyclerViewStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        dashboardViewModel.stories.value?.let { differ.submitData(it) }

        // result

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(data, differ.snapshot())
        Assert.assertEquals(data.size, differ.snapshot().size)
        Assert.assertEquals(data[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when get Stories map return success`() {
        // given
        val result = DummyData.generateData()
        val responseSuccess = Resource.success(result)

        // when
        coEvery {
            storiesRepository.getStoriesForMap()
        } returns flow {
            emit(responseSuccess)
        }
        dashboardViewModel.getStoriesMap()

        verifyOrder {
            storiesMapObserver.onChanged(responseSuccess)
        }

        Assert.assertEquals(Resource.Status.SUCCESS, dashboardViewModel.storiesMap.value?.status)
        Assert.assertEquals(result.size, dashboardViewModel.storiesMap.value?.data?.size)
    }

    @Test
    fun `when get Stories map return failed`() {
        // given
        val responseSuccess = Resource.error(ErrorResponse(true, "Kendala pada server"), null)

        // when
        coEvery {
            storiesRepository.getStoriesForMap()
        } returns flow {
            emit(responseSuccess)
        }
        dashboardViewModel.getStoriesMap()

        verifyOrder {
            storiesMapObserver.onChanged(responseSuccess)
        }
        Assert.assertEquals(Resource.Status.ERROR, dashboardViewModel.storiesMap.value?.status)
    }
}
