package com.opninterviewservice.testapp.dagger.modules

import com.opninterviewservice.testapp.interfaces.rest.retrofit.RetrofitPeopleInfoAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


//This File Created at 23.10.2020 16:22.

@Module
class RetrofitApiImplModule {

    @Provides
    @Singleton
    fun provideRetrofitApiImpl(retrofit: Retrofit): RetrofitPeopleInfoAPI {
        return retrofit.create(RetrofitPeopleInfoAPI::class.java)
    }
}