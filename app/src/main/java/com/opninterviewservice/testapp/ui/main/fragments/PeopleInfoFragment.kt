package com.opninterviewservice.testapp.ui.main.fragments


//This File Created at 20.10.2020 12:41.
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.BaseViewModel
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PeopleInfoFragmentViewModel
import kotlinx.android.synthetic.main.people_list_fragment.*

const val ID_KEY = "id"

class PeopleInfoFragment : Fragment(R.layout.people_list_fragment) {

    companion object {
        fun newInstance(args: Bundle): PeopleInfoFragment {
            val fragment = PeopleInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: PeopleInfoFragmentViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get((PeopleInfoFragmentViewModel::class.java))
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
                    changeUIVisibility(VisibilityState.GONE)
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
                    viewModel.currentPeople?.apply {
                        emptyMessage.visibility = View.GONE
//                        TODO set UI values here
                        changeUIVisibility(VisibilityState.VISIBLE)
                         } ?: run {
                        emptyMessage.visibility = View.VISIBLE
                        changeUIVisibility(VisibilityState.GONE)
                    }
                }
            }
        })
    }

    private fun changeUIVisibility(visibility: VisibilityState){

        idsItems.visibility = visibility.state
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestPeopleInfo(arguments?.getString(ID_KEY,"") ?: "")
    }

    enum class VisibilityState(val state: Int) {
        GONE(View.GONE),
        VISIBLE(View.VISIBLE)
    }
}