package com.opninterviewservice.testapp.dagger.components

import com.opninterviewservice.testapp.dagger.modules.ApiCallerModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitApiImplModule
import com.opninterviewservice.testapp.dagger.modules.RetrofitModule
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PeopleListViewModel
import com.opninterviewservice.testapp.ui.main.fragments.viewmodels.PersonInfoViewModel
import dagger.Component
import javax.inject.Singleton


//This File Created at 24.10.2020 11:23.


@Singleton
@Component(modules = [RetrofitModule::class, RetrofitApiImplModule::class, ApiCallerModule::class])
interface AppComponent {
    fun inject(personInfoFragmentViewModel: PersonInfoViewModel)
    fun inject(peopleListFragmentViewModel: PeopleListViewModel)
}