package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import com.opninterviewservice.testapp.restapi.AsyncApiCaller
import com.opninterviewservice.testapp.restapi.ShortPeopleData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper

/**
* this class demonstrates standard way how to call a async rest API
*/

class PeoplesListFragmentViewModel : BaseViewModel() {

    val peoples = ArrayList<ShortPeopleData>()

    fun requestPeoples() {
        state.value = ViewStateWrapper(UIStates.LOADING, true)
        if (peoples.isEmpty()) {
            AsyncApiCaller.getPeoples({ newPeoples ->
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