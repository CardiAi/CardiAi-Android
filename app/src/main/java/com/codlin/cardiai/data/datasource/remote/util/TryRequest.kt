package com.codlin.cardiai.data.datasource.remote.util

import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.CantConnectException
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException
import java.io.IOException

suspend fun <T, R> FlowCollector<Resource<T>>.tryRequest(tryBlock: suspend FlowCollector<Resource<T>>.() -> R): R? {
    try {
        return tryBlock()
    } catch (e: IOException) {
        e.printStackTrace()
        emit(
            Resource.Error(
                exception = CantConnectException()
            )
        )
    } catch (e: HttpException) {
        e.printStackTrace()
        emit(
            Resource.Error(
                exception = e,
            )
        )
    }
    return null
}