package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import com.opninterviewservice.testapp.restapi.ApiCaller
import com.opninterviewservice.testapp.restapi.PeopleData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper


//This File Created at 21.10.2020 14:17.
class PeopleInfoFragmentViewModel : BaseViewModel() {

    var currentPeople: PeopleData? = null

    fun requestPeopleInfo(id: String) {
        if (id.isEmpty()) {
            postError(Exception("Empty Id"))
        }
        currentPeople?.run { if(currentPeople?.id == id) {
            updateUI()
        } else {
            getPeopleInfo(id)
        } } ?: getPeopleInfo(id)
    }

    private fun getPeopleInfo(id: String){

        state.postValue(ViewStateWrapper(UIStates.LOADING, true))
        ApiCaller.getPeopleData(id, { peopleData ->
            state.postValue(ViewStateWrapper(UIStates.LOADING, false))
            currentPeople = peopleData
            updateUI()
        }, { e ->
            postError(e)
        })
    }
}