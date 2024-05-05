package com.codlin.cardiai.data.datasource.remote.interceptor

import com.codlin.cardiai.data.datasource.local.datastore.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        return runBlocking {
            chain.handleRequest(shouldAttachAuthHeader)
        }
    }

    private suspend fun Interceptor.Chain.handleRequest(shouldAttachAuthHeader: Boolean): Response {
        return if (shouldAttachAuthHeader and userPreferences.isTokenAvailable()) {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", userPreferences.getUserToken())
                    .build(),
            )
        } else {
            proceed(request())
        }
    }
}