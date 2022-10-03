package com.blank.remote.di

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import com.blank.domain.util.StoryKey.TOKEN

class AuthInterceptor(var sharedPreferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val lOriginalRequest = chain.request()
        val url = lOriginalRequest.url.toString()

        val request = lOriginalRequest.newBuilder()
            .header("X-Platform", "android")

        var finalToken =""
            sharedPreferences.getString(TOKEN, "")?.let { token ->
            if (token.isNotEmpty()) {
               finalToken= "Bearer $token"
            }
        }

        if (finalToken.isNotEmpty()) {
            request.header("Authorization", finalToken)
        }

        return chain.proceed(request.method(lOriginalRequest.method, lOriginalRequest.body).build())
    }
}