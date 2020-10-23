package com.opninterviewservice.testapp.interfaces.rest.base

import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData


//This File Created at 23.10.2020 10:50.
interface BlockingApiCaller {

    /**
     * returns a list of people the implementation must be synchronous
     * @return list of people
     */
    fun getPeople(): List<ShortPersonData>

    /**
     * returns a data of person the implementation must be synchronous
     * @param id ID of the person from the list of people
     * @return data of person
     */
    fun getPersonData(id: String): PersonData
}