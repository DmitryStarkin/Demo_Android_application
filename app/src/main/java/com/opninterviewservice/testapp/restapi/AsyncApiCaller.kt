package com.opninterviewservice.testapp.restapi

import com.google.gson.GsonBuilder
import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.interfaces.rest.retrofit.PeopleInfoAPI
import com.opninterviewservice.testapp.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//This File Created at 20.10.2020 10:40.

const val CODE_OK = 200
const val CODE_UNAUTHORIZED = 401
const val USER_DATA_LINK_PATTERN = "${BuildConfig.USER_DATA_URL}%s"
const val ERROR = "Error code"
val HEADERS = mapOf("Authorization" to BuildConfig.AUTORIZATION_HEADER)

/**
 * this class demonstrates an asynchronous call to the rest API using Retrofit
 */
object AsyncApiCaller {

    private val log = Logger(this::class.java.simpleName)
    private val retrofitAPI: PeopleInfoAPI = App.instance.retrofit.create(PeopleInfoAPI::class.java)

    fun getPeoples(onSusses: (List<ShortPeopleData>) -> Unit, onError: (Throwable) -> Unit) {

        retrofitAPI.peoplesCall(HEADERS).enqueue(object : Callback<List<ShortPeopleData>> {
            override fun onFailure(call: Call<List<ShortPeopleData>>, t: Throwable) {
                log.d { "onFailure" }
                onError.invoke(t)
            }

            override fun onResponse(call: Call<List<ShortPeopleData>>, response: Response<List<ShortPeopleData>>) {
                log.d { response.code().toString() }
                when (response.code()) {
                    CODE_OK -> {
                        log.d { response.body().toString() }
                        response.body()?.apply { onSusses.invoke(this) } ?: onError.invoke(
                            Exception(
                                "Empty response"
                            )
                        )
                    }
                    CODE_UNAUTHORIZED -> {
                        onError.invoke(
                            Exception(
                                ERROR + " " + response.code().toString() + (response.errorBody()
                                    ?: "Unauthorized")
                            )
                        )
                    }
                    else -> {
                        onError.invoke(
                            Exception(
                                "$ERROR " + response.code()
                                    .toString() + " " + (response.errorBody() ?: "Unknown error")
                            )
                        )
                    }
                }
            }
        })
    }

    fun getPeopleData(id: String, onSusses: (PeopleData) -> Unit, onError: (Throwable) -> Unit) {

        retrofitAPI.peopleInfoCall(String.format(USER_DATA_LINK_PATTERN, id), HEADERS)
            .enqueue(object : Callback<PeopleData> {
                override fun onFailure(call: Call<PeopleData>, t: Throwable) {
                    log.d { "onFailure" }
                    onError.invoke(t)
                }

                override fun onResponse(call: Call<PeopleData>, response: Response<PeopleData>) {
                    log.d { response.code().toString() }
                    when (response.code()) {
                        CODE_OK -> {
                            log.d { response.body().toString() }
                            response.body()?.apply { onSusses.invoke(this) } ?: onError.invoke(
                                Exception(
                                    "Empty response"
                                )
                            )
                        }
                        else -> {
                            onError.invoke(
                                Exception(
                                    "$ERROR " + response.code().toString() + " " + (response.errorBody()
                                        ?: "Unknown error")
                                )
                            )
                        }
                    }
                }
            })
    }
}