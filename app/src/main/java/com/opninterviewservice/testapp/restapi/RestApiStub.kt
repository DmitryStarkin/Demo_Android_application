package com.opninterviewservice.testapp.restapi

import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller


//This File Created at 23.10.2020 16:38.
class RestApiStub: AsyncApiCaller, BlockingApiCaller {

    override fun getPeople(
        onSusses: (List<ShortPersonData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getPersonData(
        id: String,
        onSusses: (PersonData) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getPeople(): List<ShortPersonData> {
        TODO("Not yet implemented")
    }

    override fun getPersonData(id: String): PersonData {
        TODO("Not yet implemented")
    }
}