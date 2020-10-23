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
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PeopleListFragmentViewModel
import kotlinx.android.synthetic.main.people_list_fragment.*


class PeopleListFragment : Fragment(R.layout.people_list_fragment) {

    companion object {
        fun newInstance() = PeopleListFragment()
    }

    private lateinit var viewModel: PeopleListFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idsItems.apply {
            adapter = PeopleAdapter(ArrayList()

            ) { peopleData ->
                (activity as MainActivity).router.moveToView(
                    FragmentRouter.FragmentRoutes.PERSON_VIEW,
                    Bundle().apply {
                        putString(ID_KEY, peopleData.id)
                    })
            }

            layoutManager = LinearLayoutManager(this.context).apply { orientation = LinearLayoutManager.VERTICAL }

            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                .apply { this.setDrawable(ContextCompat.getDrawable(context, R.drawable.item_divider)!!) })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get((PeopleListFragmentViewModel::class.java))
            viewModel.getState().observe(this as LifecycleOwner, {
                when (it?.state) {
                    BaseViewModel.UIStates.LOADING -> {
                        if (it.data == true) {
                            spinner.visibility = View.VISIBLE
                            emptyMessage.visibility = View.GONE
                        } else {
                            spinner.visibility = View.GONE
                        }
                    }

                    BaseViewModel.UIStates.ERROR -> {
                        spinner.visibility = View.GONE
                        if (it.data is Int) {
                            Toast.makeText(context!!.applicationContext,
                                it.data, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context!!.applicationContext,
                                it.data as String, Toast.LENGTH_SHORT).show()
                            emptyMessage.visibility = View.VISIBLE
                            emptyMessage.text = it.data
                        }
                    }

                    BaseViewModel.UIStates.UPDATE_UI -> {
                        spinner.visibility = View.GONE
                        if (viewModel.people.isNullOrEmpty()) {
                            emptyMessage.visibility = View.VISIBLE
                            idsItems.visibility = View.GONE
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