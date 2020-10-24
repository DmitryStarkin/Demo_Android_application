package com.opninterviewservice.testapp.restapi.apistub

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData
import com.opninterviewservice.testapp.utils.Logger
import java.lang.reflect.Type


//This File Created at 23.10.2020 16:38.
class RestApiStub(private val requestDelay: Long = 2000L) : AsyncApiCaller, BlockingApiCaller {

    private val log = Logger(this::class.java.simpleName)
    private val gson: Gson = GsonBuilder().create()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun getPeople(
        onSusses: (List<ShortPersonData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        Thread {
            try {
                val data = getPeople()
                mainHandler.post { onSusses(data) }
            } catch (t: Throwable) {
                t.printStackTrace()
                mainHandler.post { onError(t) }
            }
        }.start()
    }

    override fun getPersonData(
        id: String,
        onSusses: (PersonData) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            try {
                val data = getPersonData(id)
                mainHandler.post { onSusses(data) }
            } catch (t: Throwable) {
                t.printStackTrace()
                mainHandler.post { onError(t) }
            }
        }.start()
    }

    override fun getPeople(): List<ShortPersonData> {
        Thread.sleep(requestDelay)
        val type: Type = object : TypeToken<List<ShortPersonData>>() {}.type
        val peopleList = gson.fromJson<List<ShortPersonData>>(PEOPLE_LIST_JSON, type)
        return if (peopleList.isNullOrEmpty()) {
            throw Exception("No people data")
        } else {
            log.d{peopleList.toString()}
            peopleList
        }
    }

    override fun getPersonData(id: String): PersonData {
        Thread.sleep(requestDelay)
        val type: Type = object : TypeToken<List<PersonData>>() {}.type
        val fullPeopleInfoList =
            gson.fromJson<List<PersonData>>(PERSON_INFO_LIST_JSON, type)
        if (fullPeopleInfoList.isNullOrEmpty()) throw Exception("No full people data")
        fullPeopleInfoList.forEach {
            if (it.id == id) {
                log.d { it.toString() }
                return it
            }
        }
        throw Exception("Wrong Id")
    }
}