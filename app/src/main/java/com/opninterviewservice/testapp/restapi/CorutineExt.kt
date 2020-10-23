package com.opninterviewservice.testapp.restapi

import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
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