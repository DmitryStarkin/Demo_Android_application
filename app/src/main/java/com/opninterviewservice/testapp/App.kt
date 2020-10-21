package com.opninterviewservice.testapp

import android.app.Application
import android.content.Context
import com.opninterviewservice.testapp.settings.Settings
import com.opninterviewservice.testapp.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


//This File Created at 20.10.2020 10:15.
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val retrofit: Retrofit by lazy {

        val client = OkHttpClient().newBuilder()
            .connectTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .readTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .writeTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .apply {
                if (DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.HEADERS
                    addInterceptor(logging)
                }
            }
            .build()

        Retrofit.Builder()
            .baseUrl("http://base.com")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun getMainContext(): Context {
        return instance.getApplicationContext()
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}