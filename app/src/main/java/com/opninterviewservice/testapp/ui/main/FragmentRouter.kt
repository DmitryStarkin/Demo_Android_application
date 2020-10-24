package com.opninterviewservice.testapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.opninterviewservice.testapp.ui.main.fragments.PeopleListFragment
import com.opninterviewservice.testapp.ui.main.fragments.PersonInfoFragment


//This File Created at 20.10.2020 12:08.


class FragmentRouter(private val manager: FragmentManager, private val containerId: Int) {

    fun moveToView(rout: FragmentRoutes, data: Bundle? = null) {
        when (rout) {

            FragmentRoutes.PEOPLE_LIST -> {
                setFragment(PeopleListFragment.newInstance())
            }

            FragmentRoutes.PERSON_VIEW -> {
                data?.let {
                    setFragment(PersonInfoFragment.newInstance(it))
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        manager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    enum class FragmentRoutes {
        /**
         * Rout to
         */
        PEOPLE_LIST,
        /**
         */
        PERSON_VIEW
    }
}