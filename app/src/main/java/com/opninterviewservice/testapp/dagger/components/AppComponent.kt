package com.opninterviewservice.testapp.dagger.components

import com.opninterviewservice.testapp.dagger.modules.ApiCallerModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitApiImplModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitModule
import com.opninterviewservice.testapp.interfaces.rest.retrofit.RetrofitPeopleInfoAPI
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PeopleListFragmentViewModel
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PersonInfoFragmentViewModel
import dagger.Component
import javax.inject.Singleton


//This File Created at 24.10.2020 11:23.


@Singleton
@Component(modules = [RetrofitModule::class, RetrofitApiImplModule::class, ApiCallerModule::class])
interface AppComponent {
    fun getRetrofitPeopleInfoApiImpl(): RetrofitPeopleInfoAPI
    fun inject(personInfoFragmentViewModel: PersonInfoFragmentViewModel)
    fun inject(peopleListFragmentViewModel: PeopleListFragmentViewModel)
}