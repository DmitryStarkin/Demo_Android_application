package com.opninterviewservice.testapp.restapi.retrofit

import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.interfaces.rest.retrofit.PeopleInfoAPI
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData
import com.opninterviewservice.testapp.utils.Logger


/**
 * this class demonstrates an synchronous call to the rest API using Retrofit
 */

//This File Created at 21.10.2020 18:49.
class RetrofitBlockingApiCaller: BlockingApiCaller {

    private val log = Logger(this::class.java.simpleName)
    private val retrofitAPI: PeopleInfoAPI = App.instance.retrofit.create(PeopleInfoAPI::class.java)

    override fun getPeople(): List<ShortPersonData> {

        val response = retrofitAPI.peopleCall(HEADERS).execute()
        return when (response.code()) {
            CODE_OK -> response.body() ?: throw Exception("Empty response")
            CODE_UNAUTHORIZED ->  throw Exception(ERROR + " " + response.code().toString() + (response.errorBody() ?:"Unauthorized"))
            else -> throw Exception(ERROR + " " + response.code().toString() + " " + (response.errorBody() ?: "Unknown error"))
        }
    }

    override fun getPersonData(id: String): PersonData {

        val response = retrofitAPI.personInfoCall( String.format(USER_DATA_LINK_PATTERN, id), HEADERS).execute()
        return when (response.code()) {
            CODE_OK -> response.body() ?: throw Exception("Empty response")
            CODE_UNAUTHORIZED ->  throw Exception(ERROR + " " + response.code().toString() + (response.errorBody() ?:"Unauthorized"))
            else -> throw Exception(ERROR + " " + response.code().toString() + " " + (response.errorBody() ?: "Unknown error"))
        }
    }
}