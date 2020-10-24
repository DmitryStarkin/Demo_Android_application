package com.opninterviewservice.testapp.dagger.modules

import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.restapi.RestApiStub
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitAsyncApiCaller
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitBlockingApiCaller
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


//This File Created at 23.10.2020 16:47.

@Module
class ApiCallerModule {

    private val retrofitApiImpl = App.component.getRetrofitPeopleInfoApiImpl()

    @Provides
    @Singleton
    fun provideBlockingApiCaller(): BlockingApiCaller {

        return if (BuildConfig.REST_API_STUB_ENABLED) {
            RetrofitBlockingApiCaller(retrofitApiImpl)
        } else {
            RestApiStub()
        }
    }

    @Provides
    @Singleton
    fun provideAsyncApiCaller(): AsyncApiCaller {

        return if (BuildConfig.REST_API_STUB_ENABLED) {
            RetrofitAsyncApiCaller(retrofitApiImpl)
        } else {
            RestApiStub()
        }
    }
}