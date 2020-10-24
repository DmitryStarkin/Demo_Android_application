package com.opninterviewservice.testapp.interfaces


//This File Created at 20.10.2020 10:19.
interface Settings {

    val DEFAULT_NETWORK_TIMEOUT: Long
        get() = 30L

    var networkResponseTimeOut: Long
}