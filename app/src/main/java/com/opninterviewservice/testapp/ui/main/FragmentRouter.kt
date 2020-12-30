package com.opninterviewservice.testapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.opninterviewservice.testapp.ui.main.fragments.PeopleListFragment
import com.opninterviewservice.testapp.ui.main.fragments.PersonInfoFragment


//This File Created at 20.10.2020 12:08.


class FragmentRouter(private val manager: FragmentManager, private val containerId: Int) {

    fun moveToView(rout: FragmentRoutes, data: Bundle? = null) {
            setFragment(rout.initFragment.invoke().apply { arguments = data })
    }

    private fun setFragment(fragment: Fragment) {
        manager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    enum class FragmentRoutes(val initFragment: () -> Fragment) {
        /**
         * Rout to
         */
        PEOPLE_LIST({PeopleListFragment() }),
        /**
         */
        PERSON_VIEW({PersonInfoFragment()})
    }
}