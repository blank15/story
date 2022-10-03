package com.blank.domain.model

data class Resource<out T>(val status: Status, val data: T?, val error: ErrorResponse?, val message: String? = "") {
    companion object {
        fun <T> success(data: T?, message: String? = ""): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,

                null,
                message
            )
        }

        fun <T> error(error: ErrorResponse, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    inline fun <R> mapResource(transformer: (T?) -> R): Resource<R> {
        return when (status) {
            Status.SUCCESS -> success(transformer(data), message)
            Status.ERROR -> error(error!!, transformer(data))
            Status.LOADING -> loading(transformer(data))
        }
    }

    fun successDataOrNull(): T? = if (status == Status.SUCCESS) data else null
}