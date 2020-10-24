package com.opninterviewservice.testapp.dagger.modules

import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.interfaces.rest.retrofit.RetrofitPeopleInfoAPI
import com.opninterviewservice.testapp.restapi.apistub.RestApiStub
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitAsyncApiCaller
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitBlockingApiCaller
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


//This File Created at 23.10.2020 16:47.

@Module
class ApiCallerModule {

    @Provides
    @Singleton
    fun provideBlockingApiCaller(retrofitApiImpl: RetrofitPeopleInfoAPI): BlockingApiCaller {

        return if (BuildConfig.REST_API_STUB_ENABLED) {
            RestApiStub(BuildConfig.REST_API_STUB_REQUEST_DELAY)
        } else {
            RetrofitBlockingApiCaller(retrofitApiImpl)
        }
    }

    @Provides
    @Singleton
    fun provideAsyncApiCaller(retrofitApiImpl: RetrofitPeopleInfoAPI): AsyncApiCaller {

        return if (BuildConfig.REST_API_STUB_ENABLED) {
            RestApiStub(BuildConfig.REST_API_STUB_REQUEST_DELAY)
        } else {
            RetrofitAsyncApiCaller(retrofitApiImpl)
        }
    }
}