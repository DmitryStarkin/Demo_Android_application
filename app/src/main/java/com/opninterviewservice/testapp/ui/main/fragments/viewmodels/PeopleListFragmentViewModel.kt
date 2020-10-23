package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import com.opninterviewservice.testapp.restapi.retrofit.RetrofitAsyncApiCaller
import com.opninterviewservice.testapp.restapi.ShortPersonData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper

/**
* this class demonstrates standard way how to call a async rest API
*/

class PeopleListFragmentViewModel : BaseViewModel() {

    val people = ArrayList<ShortPersonData>()
    val restApi = RetrofitAsyncApiCaller()

    fun requestPeople() {
        state.value = ViewStateWrapper(UIStates.LOADING, true)
        if (people.isEmpty()) {
            restApi.getPeople({ newPeoples ->
                people.clear()
                people.addAll(newPeoples)
                updateUI()
            }, { e ->
                setError(e)
            })
        } else {
            updateUI()
        }
    }
}