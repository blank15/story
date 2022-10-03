package com.blank.home.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.domain.model.Resource
import com.blank.domain.repository.StoriesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

class AddStoryViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {

    private var _uploadResult = MutableLiveData<Resource<Any>>()
    val uploadResult: LiveData<Resource<Any>> get() = _uploadResult
    private var uploadStoryJob: Job? = null

    fun uploadStory(desc: String, image: File) {
        uploadStoryJob = viewModelScope.launch {
            storiesRepository.uploadStory(desc, image).onEach {
                _uploadResult.value = it
            }.collect()
        }
    }

    fun cancelJob() {
        uploadStoryJob?.cancel()
    }
}