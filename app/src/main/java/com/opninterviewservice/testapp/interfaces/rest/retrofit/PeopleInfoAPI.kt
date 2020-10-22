package com.opninterviewservice.testapp.interfaces.rest.retrofit

import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.restapi.PeopleData
import com.opninterviewservice.testapp.restapi.ShortPeopleData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url


//This File Created at 20.10.2020 10:37.
interface PeopleInfoAPI {

    @GET(BuildConfig.API_ID_URL)
    fun peoplesCall(@HeaderMap headerMap: Map<String, String>): Call<List<ShortPeopleData>>

    @GET
    fun peopleInfoCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<PeopleData>
}