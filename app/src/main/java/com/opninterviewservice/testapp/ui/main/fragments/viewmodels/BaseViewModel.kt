package com.opninterviewservice.testapp.ui.main.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.ui.main.ViewStateWrapper
import com.opninterviewservice.testapp.utils.Logger


//This File Created at 21.10.2020 16:10.
abstract class BaseViewModel : ViewModel() {

    protected val state = MutableLiveData<ViewStateWrapper<UIStates, Any?>>()
    fun getState(): LiveData<ViewStateWrapper<UIStates, Any?>> = state

    protected val log = Logger(this::class.java.simpleName)

    fun postError(error: Throwable) {
        state.postValue(
            ViewStateWrapper(
                UIStates.ERROR,
                error.message ?: R.string.undefined_error_message
            )
        )
    }

    fun setError(error: Throwable) {
        state.value = ViewStateWrapper(
            UIStates.ERROR,
            error.message ?: R.string.undefined_error_message
        )
    }

    fun updateUI() {
        state.value = ViewStateWrapper(
            UIStates.UPDATE_UI,
            null
        )
    }

    /**
     * View states
     */
    enum class UIStates {
        LOADING,
        ERROR,
        UPDATE_UI
    }
}