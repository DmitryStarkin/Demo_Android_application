package com.opninterviewservice.testapp.ui.main


//This File Created at 20.10.2020 11:58.
class ViewStateWrapper<S : Enum<*>?, D>(state: S, data: D? = null) {
    /**
     * State of view to be rendered
     *
     * Must be inherited from enum defined in ViewModel
     */
     val state: S

    /**
     * Data required for rendering current state
     */
    val data: D?


    init {
        this.state = state
        this.data = data
    }
}
