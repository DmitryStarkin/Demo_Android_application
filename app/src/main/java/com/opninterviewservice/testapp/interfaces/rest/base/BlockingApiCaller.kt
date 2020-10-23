package com.opninterviewservice.testapp.interfaces.rest.base

import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.restapi.ShortPersonData


//This File Created at 23.10.2020 10:50.
interface BlockingApiCaller {

    fun getPeople(): List<ShortPersonData>

    fun getPersonData(id: String): PersonData
}