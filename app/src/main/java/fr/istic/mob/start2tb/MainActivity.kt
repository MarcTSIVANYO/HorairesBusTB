package fr.istic.mob.start2tb

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import fr.istic.mob.start2tb.fragments.ArretBus
import fr.istic.mob.start2tb.fragments.HoraireBus
import fr.istic.mob.start2tb.fragments.ListBus
import fr.istic.mob.start2tb.fragments.RouteBus

class MainActivity :  AppCompatActivity(){

      private lateinit var fmanager  : FragmentManager
      private lateinit var fragment  : Fragment
      private var listBus : ListBus = ListBus()

    override fun onCreate(savedInstanceState: Bundle?) {
       //test()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fmanager = supportFragmentManager
        if(savedInstanceState == null){
            fragment = listBus
            fmanager.beginTransaction().replace(R.id.frame, listBus, "listeBus").addToBackStack("ListeBus").commit()

           }
        if(savedInstanceState != null){
            Toast.makeText(this, "oncreate", Toast.LENGTH_SHORT).show()
        }
    }


    fun test(){
        Log.d("test : ", "message :")
        var cursor: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null
        val uriRoute: Uri = Uri.parse(StarContract.BusRoutes.CONTENT_URI.toString())
        cursor = contentResolver?.query(uriRoute, projection, selection, selectionArgs, sortOrder)

        if(cursor!=null && cursor.moveToFirst()){
            do {
                var name = cursor?.getString(cursor?.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID))
                Log.d("test : ", "message :"+name)
            }while (cursor.moveToNext())
        }
        cursor?.close()
    }
 }