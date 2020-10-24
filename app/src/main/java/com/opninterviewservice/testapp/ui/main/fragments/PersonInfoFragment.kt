package com.opninterviewservice.testapp.ui.main.fragments


//This File Created at 20.10.2020 12:41.
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.opninterviewservice.testapp.R
import com.opninterviewservice.testapp.restapi.PersonData
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.BaseViewModel
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PersonInfoFragmentViewModel
import kotlinx.android.synthetic.main.people_info_fragment.*
import kotlinx.android.synthetic.main.people_list_fragment.emptyMessage
import kotlinx.android.synthetic.main.people_list_fragment.spinner

const val ID_KEY = "id"

class PersonInfoFragment : Fragment(R.layout.people_info_fragment) {

    companion object {
        fun newInstance(args: Bundle): PersonInfoFragment {
            val fragment = PersonInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: PersonInfoFragmentViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get((PersonInfoFragmentViewModel::class.java))
        viewModel.getState().observe(this as LifecycleOwner, {
            when (it?.state) {
                BaseViewModel.UIStates.LOADING -> {
                    if (it.data == true) {
                        changeUIVisibility(VisibilityState.GONE)
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
                        Toast.makeText(
                            context!!.applicationContext,
                            it.data, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context!!.applicationContext,
                            it.data as String, Toast.LENGTH_SHORT
                        ).show()
                        emptyMessage.visibility = View.VISIBLE
                        emptyMessage.text = it.data
                    }
                }

                BaseViewModel.UIStates.UPDATE_UI -> {
                    spinner.visibility = View.GONE
                    viewModel.currentPerson?.apply {
                        emptyMessage.visibility = View.GONE
                        setUIValues(this)
                        changeUIVisibility(VisibilityState.VISIBLE)
                    } ?: run {
                        emptyMessage.visibility = View.VISIBLE
                        changeUIVisibility(VisibilityState.GONE)
                    }
                }
            }
        })
    }

    private fun changeUIVisibility(visibility: VisibilityState) {

        firstName.visibility = visibility.state
        firstNameValue.visibility = visibility.state
        lastName.visibility = visibility.state
        lastNameValue.visibility = visibility.state
        age.visibility = visibility.state
        ageValue.visibility = visibility.state
        country.visibility = visibility.state
        countryValue.visibility = visibility.state
    }

    private fun setUIValues(data: PersonData) {

        firstNameValue.text = data.firstName
        lastNameValue.text = data.lastName
        ageValue.text = data.age.toString()
        countryValue.text = data.country
    }

    override fun onStart() {
        super.onStart()
        viewModel.requestPersonInfo(arguments?.getString(ID_KEY, "") ?: "")
    }

    enum class VisibilityState(val state: Int) {
        GONE(View.GONE),
        VISIBLE(View.VISIBLE)
    }
}