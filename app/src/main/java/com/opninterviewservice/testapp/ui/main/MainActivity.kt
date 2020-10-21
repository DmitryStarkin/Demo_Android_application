package com.opninterviewservice.testapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.opninterviewservice.testapp.R

class MainActivity : AppCompatActivity() {

    lateinit var router: FragmentRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        router = FragmentRouter(supportFragmentManager, R.id.container)
        if (savedInstanceState == null) {
            router.moveToView(FragmentRouter.FragmentRoutes.ID_LIST)
        }
    }
}