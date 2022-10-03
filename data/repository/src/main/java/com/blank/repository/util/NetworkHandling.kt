package com.blank.repository.util

import android.accounts.NetworkErrorException
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.blank.domain.model.ErrorResponse
import com.blank.domain.model.Resource
import com.blank.model.ErrorResponseRetrofit
import com.blank.repository.BuildConfig
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

abstract class NetworkHandling<ResultType, RequestType>{

    private val result: Flow<Resource<ResultType>>

    init {
        result = flow {
            emit(Resource.loading(null))
            tryToConnect()
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.tryToConnect(){
        try {
            fetchFromNetwork()
        } catch (e: Exception) {
            when (e) {
                is NetworkErrorException, is UnknownHostException -> {
                    emit(Resource.error(
                        ErrorResponse(error = true, message = "Tidak ada koneksi internet"), null))
                }
                else -> emit(Resource.error(
                    ErrorResponse(message = if (BuildConfig.DEBUG) e.localizedMessage else "Maaf, terjadi kesalahan pada server", error = true), null))
            }
        }
    }

    val asFlow: Flow<Resource<ResultType>>
        get() = result

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromNetwork() {
        createCallAsync().apply {
            when {
                isSuccessful -> {
                    val result = processResponse(body())
                    Timber.d("Success Response : $result")
                    emit(Resource.success(result, message()))
                }
                else ->{
                    val error = Gson().fromJson(errorBody()?.string(), ErrorResponseRetrofit::class.java)
                    emit(Resource.error(ErrorResponse(true, error.message ?: ""),null))
                }
            }
        }
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType?): ResultType?

    @MainThread
    protected abstract suspend fun createCallAsync(): Response<RequestType>

}