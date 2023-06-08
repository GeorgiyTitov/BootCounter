package com.georgiytitov.boottracker.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BootCompletedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == Intent.ACTION_BOOT_COMPLETED) {
                context?.let {
                    val bootEventDB = BootEventDB.getInstance(context)
                    bootEventDB.let {
                        val bootEventDao = bootEventDB.getBootEventDao()
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                bootEventDao.insertBootEvent(BootEvent(System.currentTimeMillis()))
                            } finally {
                                cancel()
                            }
                        }
                    }
                }
            }
        }
    }
}