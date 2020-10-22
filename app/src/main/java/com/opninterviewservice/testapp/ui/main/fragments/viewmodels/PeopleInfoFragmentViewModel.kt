package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import androidx.lifecycle.viewModelScope
import com.opninterviewservice.testapp.restapi.BlockingApiCaller
import com.opninterviewservice.testapp.restapi.PeopleData
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


//This File Created at 21.10.2020 14:17.

/**
 * this class demonstrates how to call a rest API using coroutines
 */
class PeopleInfoFragmentViewModel : BaseViewModel() {

    var currentPeople: PeopleData? = null

    fun requestPeopleInfo(id: String) {
        if (id.isEmpty()) {
            setError(Exception("Empty Id"))
        }
        currentPeople?.run { if(currentPeople?.id == id) {
            updateUI()
        } else {
            getPeopleInfo(id)
        } } ?: getPeopleInfo(id)
    }

    private fun getPeopleInfo(id: String) {

        state.value = ViewStateWrapper(UIStates.LOADING, true)

        viewModelScope.launch(Dispatchers.IO) {
            val data = try {
                BlockingApiCaller.getPeopleData(id)
            } catch (t: Throwable) {
                postError(t)
                cancel()
            }
            viewModelScope.launch(Dispatchers.Main) {
                currentPeople = data as PeopleData
                updateUI()
            }
        }
    }
}