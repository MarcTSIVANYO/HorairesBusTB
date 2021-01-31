package fr.istic.mob.horairesbustb.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import fr.istic.mob.horairesbustb.StarContract
import fr.istic.mob.horairesbustb.database.AppDatabase
import fr.istic.mob.horairesbustb.utils.DATABASE_NAME

class MyContentProvider : ContentProvider() {

    private  var db: AppDatabase?=null

    override fun onCreate(): Boolean {
        if (context == null) {
            throw  NullPointerException()
        }

        db = Room.databaseBuilder(
                context!!,
                AppDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries()
                .fallbackToDestructiveMigrationFrom(1, 2, 3, 4)
                .build()
        //db = AppDatabase.getInstance(context!!)

        return true
    }


    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        var cursor: Cursor? = null
        if (getContext() != null) {
            when (uri.toString()) {
                StarContract.BusRoutes.CONTENT_URI.toString() ->
                    if(selectionArgs.isNullOrEmpty()){
                        cursor = db?.routeDao()?.getAllDataRoute()
                    }else{
                        cursor = db?.routeDao()?.getDirectionRoute(selectionArgs?.get(0))
                    }

                StarContract.Trips.CONTENT_URI.toString() -> cursor = db?.tripDao()?.getAllDataTrip()

                StarContract.Stops.CONTENT_URI.toString() ->cursor =db?.stopDao()?.getStops("0001", "1")
                    /*if(selectionArgs.isNullOrEmpty()){
                        //cursor = db?.stopDao()?.getAllDataStop()
                    }else{
                        cursor = db?.stopDao()?.getStops(selectionArgs?.get(0), selectionArgs?.get(1))
                    }*/
                else -> throw IllegalArgumentException("Unknown URI")
            }
        }
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}