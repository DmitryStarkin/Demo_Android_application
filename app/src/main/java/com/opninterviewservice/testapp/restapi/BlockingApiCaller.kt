package com.opninterviewservice.testapp.restapi

import com.google.gson.GsonBuilder
import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.interfaces.rest.retrofit.PeopleInfoAPI
import com.opninterviewservice.testapp.utils.Logger


/**
 * this class demonstrates an synchronous call to the rest API using Retrofit
 */

//This File Created at 21.10.2020 18:49.
object BlockingApiCaller {

    private val log = Logger(this::class.java.simpleName)
    private val retrofitAPI: PeopleInfoAPI = App.instance.retrofit.create(PeopleInfoAPI::class.java)
    val gson = GsonBuilder().create()

    fun getPeoples(): List<ShortPeopleData> {

        val response = retrofitAPI.peoplesCall(HEADERS).execute()
        return when (response.code()) {
            CODE_OK -> gson.fromJson<List<ShortPeopleData>>(response.body(), ShortPeopleData::class.java)
            CODE_UNAUTHORIZED ->  throw Exception(ERROR + " " + response.code().toString() + (response.errorBody() ?:"Unauthorized"))
            else -> throw Exception(ERROR + " " + response.code().toString() + " " + (response.errorBody() ?: "Unknown error"))
        }
    }

    fun getPeopleData(id: String): PeopleData{

        val response = retrofitAPI.peopleInfoCall( String.format(USER_DATA_LINK_PATTERN, id), HEADERS).execute()
        return when (response.code()) {
            CODE_OK -> gson.fromJson(response.body(), PeopleData::class.java)
            CODE_UNAUTHORIZED ->  throw Exception(ERROR + " " + response.code().toString() + (response.errorBody() ?:"Unauthorized"))
            else -> throw Exception(ERROR + " " + response.code().toString() + " " + (response.errorBody() ?: "Unknown error"))
        }
    }
}