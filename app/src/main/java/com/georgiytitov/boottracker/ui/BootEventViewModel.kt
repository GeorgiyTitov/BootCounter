package com.georgiytitov.boottracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.georgiytitov.boottracker.R
import com.georgiytitov.boottracker.data.BootEventDB
import java.util.concurrent.TimeUnit

class BootEventViewModel(private val application: Application) : AndroidViewModel(application) {

    private val bootEventDB = BootEventDB.getInstance(application)

    val bootEvents: LiveData<String> = bootEventDB.getBootEventDao().getAllBootEventsLive().map {
        if (it.isEmpty()) {
            application.getString(R.string.no_boot_events_detected)
        } else {
            var text = ""
            it.forEachIndexed { index, element ->
                text += "${index + 1} - ${element.bootTime}\n"
            }
            text
        }
    }

    fun runNotificationWorker(hasNotificationPermissionGranted: Boolean) {
        if (hasNotificationPermissionGranted) {
            val periodicWorkRequest =
                PeriodicWorkRequestBuilder<BootNotificationWorker>(15, TimeUnit.MINUTES)
                    .build()
            // Enqueueing work request with such function, is used in order to run only one (unique)
            // work request. Also we can use different ExistingPeriodicWorkPolicy and reschedule
            // request and it's period or not. Current usage, run new request with new period, every
            // time it is called.
            WorkManager.getInstance(application)
                .enqueueUniquePeriodicWork(
                    "BootNotificationWorker",
                    ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                    periodicWorkRequest
                )


        }
    }
}