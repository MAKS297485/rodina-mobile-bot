package com.rodina.mobilebot.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AfkAutomationService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("AfkAutomationService", "Automation service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AfkAutomationService", "Automation service started")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AfkAutomationService", "Automation service stopped")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
