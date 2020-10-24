package com.opninterviewservice.testapp

import android.app.Application
import android.content.Context
import com.opninterviewservice.testapp.dagger.components.AppComponent
import com.opninterviewservice.testapp.dagger.components.DaggerAppComponent
import com.opninterviewservice.testapp.dagger.modules.ApiCallerModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitApiImplModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitModule


//This File Created at 20.10.2020 10:15.
class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var component: AppComponent
    }

    fun getMainContext(): Context {
        return instance.getApplicationContext()
    }

    override fun onCreate() {
        instance = this
        component = DaggerAppComponent.builder()
            .apiCallerModule(ApiCallerModule())
            .retrofitApiImplModule(RetrofitApiImplModule())
            .retrofitModule(RetrofitModule())
            .build()

        super.onCreate()
    }
}