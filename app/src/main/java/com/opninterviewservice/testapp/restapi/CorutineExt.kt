package com.opninterviewservice.testapp.restapi

import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


//This File Created at 22.10.2020 12:51.

/**
 * extension functions for executing requests from coroutines
 */

suspend fun AsyncApiCaller.getPersonData(id: String): PersonData {

    return suspendCoroutine { continuation ->
        this.getPersonData(id, { peopleData ->
            continuation.resume(peopleData)
        }, { e ->
            continuation.resumeWithException(e)
        })
    }
}

/**
 * extension functions for executing requests from coroutines
 */

suspend fun AsyncApiCaller.getPeople(): List<ShortPersonData> {

    return suspendCoroutine { continuation ->
        this.getPeople({ peoples ->
            continuation.resume(peoples)
        }, { e ->
            continuation.resumeWithException(e)
        })
    }
}

/**
 * universal extension functions for executing async retrofit requests from coroutines
 */

suspend fun <T> Call<T>.enqueueSuspend(): T {
    return suspendCoroutine { continuation ->
        this.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.apply { continuation.resume(this) }
                        ?: continuation.resumeWithException(Exception("Empty body"))
                } else {
                    continuation.resumeWithException(
                        Exception(
                            response.code().toString() + (response.errorBody() ?: "")
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}