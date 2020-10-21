package com.opninterviewservice.testapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.opninterviewservice.testapp.ui.main.fragments.PeoplesListFragment
import com.opninterviewservice.testapp.ui.main.fragments.PeopleInfoFragment


//This File Created at 20.10.2020 12:08.


class FragmentRouter(private val manager: FragmentManager, private val containerId: Int) {

    fun moveToView(rout: FragmentRoutes, data: Bundle? = null) {
        when (rout) {

            FragmentRoutes.ID_LIST -> {
                setFragment(PeoplesListFragment.newInstance())
            }

            FragmentRoutes.USER_VIEW -> {
                data?.let {
                    setFragment(PeopleInfoFragment.newInstance(it))
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        manager.beginTransaction()
            .replace(containerId, fragment)
            .commitNow()
    }

    enum class FragmentRoutes {
        /**
         * Rout to
         */
        ID_LIST,
        /**
         */
        USER_VIEW
    }
}