package com.opninterviewservice.testapp.utils

import android.util.Log
import com.opninterviewservice.testapp.BuildConfig.DEBUG


//This File Created at 20.10.2020 11:09.
class Logger(var tag: String) {

    fun d(perform: Boolean = true, msg: () -> String) {
        if (DEBUG && perform ) {
            val _msg = msg.invoke()
            Log.d(
                tag, _msg
            )

        }
    }
}