package fr.istic.mob.start2tb.fragments

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import fr.istic.mob.horairesbustb.model.StopTime
import fr.istic.mob.start2tb.Data.RouteData
import fr.istic.mob.start2tb.MainActivity
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract


class RouteBus(idTrip: String, time: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var listeBusRoute: ArrayList<StopTime> =  ArrayList<StopTime>()
    private val adapter: RouteData ? = null
    private lateinit var listener : OnFragmentInteractionListener
    private lateinit var listView: ListView
    private lateinit var activity: MainActivity
    private lateinit var time: String
    private lateinit var idTrip: String
    private  final  var TAG: String = "fragment4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "oncreate")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
        Log.d(TAG, "onAttach")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_route_bus, container, false)
        this.listView = view.findViewById(R.id.listViewHoraire) as ListView
        this.listeBusRoute =  chargerInfosRoute(this.idTrip, this.time) as java.util.ArrayList<StopTime>
        var data: ArrayAdapter<StopTime> = RouteData(context!!, 0, listeBusRoute)
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
    /*override fun onDetach() {
        super.onDetach()
        listener = null
    }*/

    fun chargerInfosRoute(tripId: String, heure: String) {
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        val uriHoraire: Uri = Uri.parse("content://fr.istic.mob.horairesbustb/routedetail")
        val selectionArgs: Array<String>? = arrayOf<String>(tripId, heure)
        val horaires: ArrayList<StopTime> = ArrayList<StopTime>()
        c = activity?.managedQuery(uriHoraire, projection, selection, selectionArgs, sortOrder)
        if (c != null) {
            while (c.moveToNext()) {
                var busStopTime: StopTime = StopTime(
                    0,
                    c.getString(c.getColumnIndex(StarContract.StopTimes.StopTimeColumns.TRIP_ID)),
                    c.getString(c.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME)),
                    c.getString(c.getColumnIndex(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME)),
                    c.getString(c.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_ID)),
                    c.getString(c.getColumnIndex(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE)),
                    null,
                    null,
                    null,
                    null
                )
                //recuperer le nom de l'arret
                horaires.add(busStopTime)

            }
            c.close()

        }

    }
}