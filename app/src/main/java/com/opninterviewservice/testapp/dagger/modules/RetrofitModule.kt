package com.opninterviewservice.testapp.dagger.modules

import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.settings.Settings
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


//This File Created at 23.10.2020 16:17.

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val client = OkHttpClient().newBuilder()
            .connectTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .readTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .writeTimeout(Settings.cloudResponseTimeOut, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.HEADERS
                    addInterceptor(logging)
                }
            }
            .build()

        return Retrofit.Builder()
//                stub for the base address (the real address is defined in interfaces)
            .baseUrl("http://base.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}