package com.opninterviewservice.testapp.interfaces.rest.base

import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData


//This File Created at 23.10.2020 10:49.
interface AsyncApiCaller {

    fun getPeople(onSusses: (List<ShortPersonData>) -> Unit, onError: (Throwable) -> Unit)

    fun getPersonData(id: String, onSusses: (PersonData) -> Unit, onError: (Throwable) -> Unit)
}