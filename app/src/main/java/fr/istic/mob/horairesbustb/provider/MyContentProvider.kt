package fr.istic.mob.horairesbustb.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import fr.istic.mob.horairesbustb.StarContract
import fr.istic.mob.horairesbustb.database.AppDatabase
import fr.istic.mob.horairesbustb.utils.DATABASE_NAME

class MyContentProvider : ContentProvider() {

     private lateinit var db:  AppDatabase

    override fun onCreate(): Boolean {
         if(context==null){
             throw  NullPointerException()
         }
        db = Room.databaseBuilder(
             context!!,
             AppDatabase::class.java, DATABASE_NAME
         ).allowMainThreadQueries()
             .fallbackToDestructiveMigrationFrom(1,2,3,4)
             .build()

         return true
     }


     override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                        selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
         when (uri.toString()){
             StarContract.BusRoutes.CONTENT_URI.toString()->db.routeDao().getAllDataRoute()
             StarContract.Trips.CONTENT_URI.toString()->db.tripDao().getAllDataTrip()
             StarContract.Stops.CONTENT_URI.toString()->db.stopDao().getAllDataStop()
             StarContract.BusRoutes.CONTENT_URI.toString()->db.routeDao().getDirectionRoute(selectionArgs?.get(0))
             StarContract.Stops.CONTENT_URI.toString()->db.stopDao().getStops(selectionArgs?.get(0),selectionArgs?.get(1))
         }

         throw UnsupportedOperationException("Not yet implemented")
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