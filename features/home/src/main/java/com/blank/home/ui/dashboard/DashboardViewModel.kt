package com.blank.home.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import com.blank.model.database.StoryModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {

    private val _stories = MutableLiveData<PagingData<StoryModel>>()
    val stories: LiveData<PagingData<StoryModel>> get() = _stories

    private val _storiesMap = MutableLiveData<Resource<List<StoryModel>>>()
    val storiesMap: LiveData<Resource<List<StoryModel>>> get() = _storiesMap

    fun getStories() {
        viewModelScope.launch {
            storiesRepository.getStories()
                .cachedIn(viewModelScope)
                .onEach {
                    _stories.value = it
                }.collect()
        }
    }

    fun getStoriesMap() {
        viewModelScope.launch {
            storiesRepository.getStoriesForMap().onEach {
                _storiesMap.value = it
            }.collect()
        }
    }

    fun logout() {
        storiesRepository.logout()
    }
}
