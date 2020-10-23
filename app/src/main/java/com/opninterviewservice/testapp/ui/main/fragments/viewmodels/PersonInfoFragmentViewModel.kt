package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import androidx.lifecycle.viewModelScope
import com.opninterviewservice.testapp.restapi.*
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitAsyncApiCaller
import com.opninterviewservice.testapp.restapi.retrofit.RetrofitBlockingApiCaller
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


//This File Created at 21.10.2020 14:17.

/**
 * this class demonstrates how to call a rest API using coroutines
 */
class PersonInfoFragmentViewModel : BaseViewModel() {

    var currentPerson: PersonData? = null
    val asyncRestApi = RetrofitAsyncApiCaller()
    val blockingRestApi = RetrofitBlockingApiCaller()

    fun requestPersonInfo(id: String) {
        if (id.isEmpty()) {
            setError(Exception("Empty Id"))
        }
        currentPerson?.run {
            if (currentPerson?.id == id) {
                updateUI()
            } else {
                getPersonInfo(id)
            }
        } ?: getPersonInfo(id)
    }

    private fun getPersonInfo(id: String) {

        state.value = ViewStateWrapper(UIStates.LOADING, true)

        viewModelScope.launch(Dispatchers.IO) {
            val data = try {
                blockingRestApi.getPersonData(id)
            } catch (t: Throwable) {
                postError(t)
//                we can remove cancel here, then the currentPeople variable will get a null value
//                and this situation will be processed in the fragment as an empty profile
//                otherwise an error message will be displayed
                cancel()
                null
            }
            launch(Dispatchers.Main) {
                currentPerson = data
                updateUI()
            }
        }
    }

    /**
     * this function does the same as the previous one but runs the coroutine on the main thread and uses the suspend function
     */
    private fun getPersonInfo2(id: String) {

        state.value = ViewStateWrapper(UIStates.LOADING, true)
        viewModelScope.launch {
            currentPerson = try {
                asyncRestApi.getPersonData(id) //suspend function
            } catch (t: Throwable) {
                postError(t)
//                we can remove cancel here, then the currentPeople variable will get a null value
//                and this situation will be processed in the fragment as an empty profile
//                otherwise an error message will be displayed
                cancel()
                null
            }
            updateUI()
        }
    }
}