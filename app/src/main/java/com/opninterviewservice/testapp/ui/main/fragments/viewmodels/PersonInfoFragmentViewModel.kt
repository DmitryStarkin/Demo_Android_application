package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import androidx.lifecycle.viewModelScope
import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.interfaces.rest.base.AsyncApiCaller
import com.opninterviewservice.testapp.interfaces.rest.base.BlockingApiCaller
import com.opninterviewservice.testapp.restapi.*
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


//This File Created at 21.10.2020 14:17.

/**
 * this class demonstrates how to call a rest API using coroutines
 */
class PersonInfoFragmentViewModel : BaseViewModel() {

    init {
        App.component.inject(this)
    }

    var currentPerson: PersonData? = null

    @Inject
    lateinit var asyncRestApi: AsyncApiCaller

    @Inject
    lateinit var blockingRestApi: BlockingApiCaller

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

        state.value = ViewStateWrapper(UIStates.LOADING)

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

        state.value = ViewStateWrapper(UIStates.LOADING)
        viewModelScope.launch {
            currentPerson = try {
                asyncRestApi.getPersonData(id) //suspend function
            } catch (t: Throwable) {
                setError(t)
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