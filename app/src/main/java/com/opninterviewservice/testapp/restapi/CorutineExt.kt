package com.opninterviewservice.testapp.restapi

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


//This File Created at 22.10.2020 12:51.

/**
 * extension functions for executing requests from coroutines
 */

suspend fun AsyncApiCaller.getPeopleData(id: String): PeopleData {

    return suspendCoroutine { continuation ->
        this.getPeopleData(id, { peopleData ->
            continuation.resume(peopleData)
        }, { e ->
            continuation.resumeWithException(e)
        })
    }
}

suspend fun AsyncApiCaller.getPeoples(): List<ShortPeopleData> {

    return suspendCoroutine { continuation ->
        this.getPeoples({ peoples ->
            continuation.resume(peoples)
        }, { e ->
            continuation.resumeWithException(e)
        })
    }
}