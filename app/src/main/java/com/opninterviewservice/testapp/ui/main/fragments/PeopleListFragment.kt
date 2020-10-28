package com.opninterviewservice.testapp.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.ui.main.FragmentRouter
import com.opninterviewservice.testapp.ui.main.MainActivity
import com.opninterviewservice.testapp.ui.main.adapters.PeopleAdapter
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.BaseViewModel
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PeopleListViewModel
import kotlinx.android.synthetic.main.people_list_fragment.*


class PeopleListFragment : Fragment(R.layout.people_list_fragment) {

    companion object {
        fun newInstance() = PeopleListFragment()
    }

    private lateinit var viewModel: PeopleListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idsItems.apply {
            adapter = PeopleAdapter(
                ArrayList()

            ) { peopleData ->
                (activity as MainActivity).router.moveToView(
                    FragmentRouter.FragmentRoutes.PERSON_VIEW,
                    Bundle().apply {
                        putString(ID_KEY, peopleData.id)
                    })
            }

            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    .apply {
                        this.setDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.item_divider
                            )!!
                        )
                    })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get((PeopleListViewModel::class.java))
        viewModel.getState().observe(this as LifecycleOwner, {
            when (it?.state) {
                BaseViewModel.UIStates.LOADING -> {
                    idsItems.visibility = View.GONE
                    emptyMessage.visibility = View.GONE
                    spinner.visibility = View.VISIBLE
                }

                BaseViewModel.UIStates.ERROR -> {
                    spinner.visibility = View.GONE
                    idsItems.visibility = View.GONE
                    if (it.data is Int) {
                        Toast.makeText(
                            context!!.applicationContext,
                            it.data, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context!!.applicationContext,
                            it.data as String, Toast.LENGTH_SHORT
                        ).show()
                        emptyMessage.text = it.data
                        emptyMessage.visibility = View.VISIBLE
                    }
                }

                BaseViewModel.UIStates.UPDATE_UI -> {
                    spinner.visibility = View.GONE
                    if (viewModel.people.isNullOrEmpty()) {
                        idsItems.visibility = View.GONE
                        emptyMessage.text = getString(R.string.empty_data_message)
                        emptyMessage.visibility = View.VISIBLE
                    } else {
                        emptyMessage.visibility = View.GONE
                        idsItems.visibility = View.VISIBLE
                        (idsItems.adapter as PeopleAdapter).setData(viewModel.people)
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestPeople()
    }
}