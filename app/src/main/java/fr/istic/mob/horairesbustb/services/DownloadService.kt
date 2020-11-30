package fr.istic.mob.horairesbustb.services

import android.app.IntentService
import android.content.Intent
import android.util.Log

private const val TAG = "DownloadService"
class DownloadService : IntentService("DownloadService ") {

    override fun onHandleIntent(intent: Intent?) {
        Log.e(TAG, "onHandleIntent: ")
    }
}