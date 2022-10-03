package com.blank.home.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import com.blank.model.story.StoryResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {

    private val _stories = MutableLiveData<Resource<List<StoryResult>>>()
    val stories: LiveData<Resource<List<StoryResult>>> get() = _stories

    private var getStoriesJob: Job? = null
    fun getStories() {
        getStoriesJob = viewModelScope.launch {
            storiesRepository.getStories().cancellable().onEach {
                _stories.value = it
            }.collect()
        }
    }

    fun logout() {
        storiesRepository.logout()
    }

    fun cancelJob() {
        getStoriesJob?.cancel()
    }
}