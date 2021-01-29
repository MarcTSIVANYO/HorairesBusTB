package fr.istic.mob.start2tb

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import fr.istic.mob.horairesbustb.model.Route
import fr.istic.mob.start2tb.Data.ListBusData

class OtherMainActivity : AppCompatActivity() {
    private val LIST_BUS = "listeBus"
    private val SEARCHED_ELEMT = "arrêt"
    private var listBus: ArrayList<Route> = ArrayList<Route>()
    private lateinit var searchStop : AutoCompleteTextView
    lateinit var textView: AutoCompleteTextView
    lateinit var btnsearchBus: Button
    lateinit var btnsearchArret: Button
    lateinit var listView: ListView
    lateinit var value : String
    lateinit var myAdapterBus : ListBusData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_activity_main)

        var stops: ArrayList<String> = this.getArret()
        var myAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            stops
        )
        textView= findViewById(R.id.barRecherche)
        textView.setAdapter(myAdapter)
        textView!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(view!!.applicationWindowToken, 0)
            }

        }
        btnsearchBus= findViewById(R.id.rechercheBus)
        btnsearchArret = findViewById(R.id.rechercheArret)
        listView = findViewById(R.id.listeBusArret)
        searchStop = findViewById(R.id.barRecherche)
        if(savedInstanceState != null){
            value = savedInstanceState.getString(SEARCHED_ELEMT, "")
            searchStop.setText(value)
            listBus = savedInstanceState.getSerializable(LIST_BUS) as ArrayList<Route>
            myAdapterBus = ListBusData(this@OtherMainActivity, 0, listBus)

            listView.adapter = myAdapterBus
            btnsearchBus.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    input.hideSoftInputFromWindow(view!!.applicationWindowToken, 0)
                    value = searchStop.text.toString()
                    if (!value.trim().equals("", ignoreCase = true)) {
                        listBus = getBus(value)
                        myAdapterBus = ListBusData( this@OtherMainActivity,0, listBus)
                        listView.setAdapter(myAdapterBus)
                    } else { Toast.makeText(applicationContext, "Aucun arret trouvé", Toast.LENGTH_SHORT).show()


                    }
                }

            })
        }
        btnsearchArret.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@OtherMainActivity,MainActivity::class.java)
                startActivity( intent)
            }

        })
        }

    override fun onSaveInstanceState(saveInstanceState: Bundle) {
        super.onSaveInstanceState(saveInstanceState)
        saveInstanceState.putSerializable(LIST_BUS, listBus)
        saveInstanceState.putSerializable(SEARCHED_ELEMT, searchStop.text.toString())

    }
    //  //Recupère les arrêts de bus
    fun getArret(): ArrayList<String>{
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        val selectionArgs: Array<String>? = null
        val uriStop: Uri = Uri.parse("content://fr.istic.mob.horairesbustb/stops/listeArret")
        val busStops: ArrayList<String> = ArrayList<String>()
        c = this.managedQuery(uriStop, projection, selection, selectionArgs, sortOrder)
       if(c != null){
           while (c.moveToNext()) {
               busStops.add(c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.NAME)))
           }

       }
        //c.close()
       return busStops
    }
    //Recupère les lignes de bus
    fun getBus(stopName: String): ArrayList<Route>{
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null
        val uriBus: Uri = Uri.parse("content://fr.istic.mob.horairesbustb/bus/arretBus")
        val buslist: ArrayList<Route> = ArrayList<Route>()
        c = this.managedQuery(uriBus, projection, selection, selectionArgs, sortOrder)
        if(c != null){
            while (c.moveToNext()) {
                var busRoute : Route = Route(
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)),
                    null,
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                    null,
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR)),
                    null

                )
                buslist.add(busRoute)
            }

        }
        c.close()
        return buslist
    }

}