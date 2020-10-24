package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.restapi.ShortPersonData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper
import javax.inject.Inject

/**
 * this class demonstrates standard way how to call a async rest API
 */

class PeopleListFragmentViewModel : BaseViewModel() {

    init {
        App.component.inject(this)
    }

    val people = ArrayList<ShortPersonData>()

    @Inject
    lateinit var restApi: AsyncApiCaller

    fun requestPeople() {
        state.value = ViewStateWrapper(UIStates.LOADING, true)
        if (people.isEmpty()) {
            restApi.getPeople({ newPeople ->
                people.clear()
                people.addAll(newPeople)
                updateUI()
            }, { e ->
                setError(e)
            })
        } else {
            updateUI()
        }
    }
}