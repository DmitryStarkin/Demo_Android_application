package com.opninterviewservice.testapp.restapi.apistub

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData


//This File Created at 23.10.2020 16:38.
class RestApiStub(private val requestDelay: Long = 2000L) : AsyncApiCaller, BlockingApiCaller {

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
                mainHandler.post { onError(t) }
            }
        }.start()
    }

    override fun getPeople(): List<ShortPersonData> {
        Thread.sleep(requestDelay)
        val peopleList =
            gson.fromJson<List<ShortPersonData>>(PEOPLE_LIST_JSON, ShortPersonData::class.java)
        return if (peopleList.isNullOrEmpty()) {
            throw Exception("No people data")
        } else {
            peopleList
        }
    }

    override fun getPersonData(id: String): PersonData {
        Thread.sleep(requestDelay)
        val fullPeopleInfoList =
            gson.fromJson<List<PersonData>>(PEOPLE_LIST_JSON, PersonData::class.java)
        if (fullPeopleInfoList.isNullOrEmpty()) throw Exception("No full people data")
        fullPeopleInfoList.forEach {
            if (it.id == id) return it
        }
        throw Exception("Wrong Id")
    }
}