package fr.istic.mob.start2tb.fragments

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import fr.istic.mob.horairesbustb.model.StopTime
import fr.istic.mob.start2tb.data.RouteData
import fr.istic.mob.start2tb.MainActivity
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract


class RouteBus() : Fragment() {
    // TODO: Rename and change types of parameters
    private var listeBusRoute: ArrayList<StopTime> =  ArrayList<StopTime>()
    private val adapter: RouteData ? = null
    private lateinit var listener : OnFragmentInteractionListener
    private lateinit var listView: ListView
    private lateinit var activity: MainActivity
    private var time: String = ""
    private  var idTrip: String =""
    private  final  var TAG: String = "fragment4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_route_bus, container, false)
        this.listView = view.findViewById(R.id.listViewHoraire) as ListView
       this.listeBusRoute =  chargerInfosRoute(this.idTrip, this.time)
        val data: ArrayAdapter<StopTime> = RouteData(context!!, 0, listeBusRoute)
        this.listView.setAdapter(data)

        return view
    }

    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri?)
    }

    fun onButtonPressed(uri: Uri?) {
        if (listener != null) {
            listener.onFragmentInteraction(uri)
        }
    }
    fun newInstance(): Fragment? {
        val mFrgment = RouteBus()
        return mFrgment
    }
    fun chargerInfosRoute(tripId: String, heure: String) : ArrayList<StopTime>{
        var cursor: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        val uriHoraire: Uri = Uri.parse(StarContract.RouteDetails.CONTENT_URI.toString())
        val selectionArgs: Array<String>? = arrayOf<String>(tripId, heure)
        val horaires: ArrayList<StopTime> = ArrayList()
        cursor = activity?.managedQuery(uriHoraire, projection, selection, selectionArgs, sortOrder)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var busStopTime = StopTime(
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE)),
                )
                //recuperer le nom de l'arret
                busStopTime.setNomArret(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)))
                horaires.add(busStopTime)

            }
        }
       cursor.close()
        return horaires
    }
}