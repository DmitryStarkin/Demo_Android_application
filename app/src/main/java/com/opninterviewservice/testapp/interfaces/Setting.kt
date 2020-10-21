package com.opninterviewservice.testapp.interfaces


//This File Created at 20.10.2020 10:19.
interface Setting {

    val MIN_CLOUD_TIMEOUT: Long
        get() = 5L
    val MAX_CLOUD_TIMEOUT: Long
        get() = 120L
    val DEFAULT_CLOUD_TIMEOUT: Long
        get() = 30L


    var cloudResponseTimeOut: Long
}