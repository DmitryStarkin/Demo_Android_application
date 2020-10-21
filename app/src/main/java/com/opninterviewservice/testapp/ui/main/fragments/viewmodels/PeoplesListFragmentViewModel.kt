package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import com.opninterviewservice.testapp.restapi.ApiCaller
import com.opninterviewservice.testapp.restapi.ShortPeopleData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper

class PeoplesListFragmentViewModel : BaseViewModel() {

    val peoples = ArrayList<ShortPeopleData>()

    fun requestPeoples() {

        if (peoples.isEmpty()) {
            state.postValue(ViewStateWrapper(UIStates.LOADING, true))
            ApiCaller.getPeoples({ newPeoples ->
                state.postValue(ViewStateWrapper(UIStates.LOADING, false))
                peoples.clear()
                peoples.addAll(newPeoples)
                updateUI()
            }, { e ->
                postError(e)
            })

        } else {
            updateUI()
        }
    }
}