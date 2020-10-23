package com.opninterviewservice.testapp.restapi.retrofit

import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.BuildConfig
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.retrofit.PeopleInfoAPI
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData
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
class RetrofitAsyncApiCaller: AsyncApiCaller {

    private val log = Logger(this::class.java.simpleName)
    private val retrofitAPI: PeopleInfoAPI = App.instance.retrofit.create(PeopleInfoAPI::class.java)

    override fun getPeople(onSusses: (List<ShortPersonData>) -> Unit, onError: (Throwable) -> Unit) {

        retrofitAPI.peopleCall(HEADERS).enqueue(object : Callback<List<ShortPersonData>> {
            override fun onFailure(call: Call<List<ShortPersonData>>, t: Throwable) {
                log.d { "onFailure" }
                onError.invoke(t)
            }

            override fun onResponse(call: Call<List<ShortPersonData>>, response: Response<List<ShortPersonData>>) {
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

    override fun getPersonData(id: String, onSusses: (PersonData) -> Unit, onError: (Throwable) -> Unit) {

        retrofitAPI.personInfoCall(String.format(USER_DATA_LINK_PATTERN, id), HEADERS)
            .enqueue(object : Callback<PersonData> {
                override fun onFailure(call: Call<PersonData>, t: Throwable) {
                    log.d { "onFailure" }
                    onError.invoke(t)
                }

                override fun onResponse(call: Call<PersonData>, response: Response<PersonData>) {
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