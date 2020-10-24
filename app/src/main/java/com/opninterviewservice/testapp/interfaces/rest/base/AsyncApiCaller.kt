package com.opninterviewservice.testapp.interfaces.rest.base

import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData


//This File Created at 23.10.2020 10:49.
interface AsyncApiCaller {

    /**
     * returns a list of people the implementation must be asynchronous
     * @param onSusses callback called on main thread in case of success
     * @param onError callback called on main thread in case of error
     */
    fun getPeople(onSusses: (List<ShortPersonData>) -> Unit, onError: (Throwable) -> Unit)


    /**
     * returns a data of person the implementation must be asynchronous
     * @param id ID of the person from the list of people
     * @param onSusses callback called on main thread in case of success
     * @param onError callback called on main thread in case of error
     */
    fun getPersonData(id: String, onSusses: (PersonData) -> Unit, onError: (Throwable) -> Unit)
}