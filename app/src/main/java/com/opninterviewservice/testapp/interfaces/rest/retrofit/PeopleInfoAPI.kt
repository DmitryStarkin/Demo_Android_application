package com.opninterviewservice.testapp.interfaces.rest.retrofit

import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url


//This File Created at 20.10.2020 10:37.
interface PeopleInfoAPI {

    @GET(BuildConfig.API_ID_URL)
    fun peopleCall(@HeaderMap headerMap: Map<String, String>): Call<List<ShortPersonData>>

    @GET
    fun personInfoCall(@Url url: String, @HeaderMap headerMap: Map<String, String>): Call<PersonData>
}