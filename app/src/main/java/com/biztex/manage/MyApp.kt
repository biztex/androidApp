package com.biztex.manage

import android.app.Application
import com.biztex.manage.utils.DatabasesHelper

class MyApp : Application() {
    companion object{
        lateinit var databasesHelper: DatabasesHelper
    }

    override fun onCreate() {
        super.onCreate()
        databasesHelper = DatabasesHelper(applicationContext)
    }

}