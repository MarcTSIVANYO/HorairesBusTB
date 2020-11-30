package fr.istic.mob.horairesbustb.services

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import fr.istic.mob.horairesbustb.utils.URL

class DownloadManagerService :IntentService("download") {
    override fun onHandleIntent(intent: Intent?) {
        val uri = Uri.parse(URL)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI) // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // This will show notification on top when downloading the file.
        request.setTitle("Téléchargement") // Title for notification.
        request.addRequestHeader("Content-type", "application/octect-stream")
        request.setMimeType("application/octet-stream")
        val downloadPathArray = URL.split("/")
        val path = "HELLO.json"
        request.setDestinationInExternalFilesDir(
                this, null,
                path
        ) // Storage directory path
        val id =
                (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request) // This will start downloading
    }


}