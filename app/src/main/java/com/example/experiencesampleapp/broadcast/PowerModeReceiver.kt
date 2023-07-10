package com.example.experiencesampleapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager


class PowerModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == PowerManager.ACTION_POWER_SAVE_MODE_CHANGED) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (powerManager.isPowerSaveMode) {
                // Device is in Low Power Mode, stop the service
                //context.stopService(Intent(context, TestService::class.java))
            } else {
                // Device exited Low Power Mode, start the service
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //context.startForegroundService(Intent(context, TestService::class.java))
                }
            }
        }
    }
}