package com.georgiytitov.boottracker

import android.app.Application
import com.georgiytitov.boottracker.data.BootEventDB

class BootTrackerApplication: Application() {

    lateinit var bootEventDB: BootEventDB

    override fun onCreate() {
        super.onCreate()
        bootEventDB = BootEventDB.getInstance(this)
    }
}