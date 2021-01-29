package fr.istic.mob.start2tb.fragments

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.horairesbustb.model.StopTime
import fr.istic.mob.start2tb.Data.HoraireData
import fr.istic.mob.start2tb.MainActivity
import fr.istic.mob.start2tb.MyListenner
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HoraireBus.newInstance] factory method to
 * create an instance of this fragment.
 */
class HoraireBus(id_arret: String, id_bus: String, dir: Int, date: String, time: String)  : Fragment() {


    private lateinit var mActivite: MainActivity
    private lateinit var myFragmentListnner: MyListenner
    private var listeHoraireBus: ArrayList<StopTime> = ArrayList<StopTime>()
    private lateinit var sData: HoraireBus
    private var dir: Int = 0
    private lateinit var id_arret: String
    private lateinit var date: String
    private lateinit var time: String
    private var TAG: String = "TITLE"
    private lateinit var listView: ListView
    lateinit var idbus: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "oncreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_horaire_bus, container, false)
        this.listView = view.findViewById(R.id.idHoraire)
        chargerInfosBusHoraire(id_arret, idbus, date, time, dir)
        var data: ArrayAdapter<StopTime> = HoraireData(context!!, 0, listeHoraireBus)
        this.listView.setAdapter(data)
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val selectedStopTime : StopTime = listeHoraireBus?.get(position)
            val idStopTime : String = selectedStopTime.tripId.toString()
            horaireSelected(idStopTime, selectedStopTime.arrival.toString() )
        }
        return view
    }


    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    fun horaireSelected(idTrip: String, time: String) {
        val routeBus: RouteBus = RouteBus(idTrip, time)
        val fragmentManager: FragmentManager? = null
        fragmentManager?.beginTransaction()?.replace(R.id.frame, routeBus, "routeBus")?.addToBackStack("routeBus")?.commit()
    }
    fun chargerInfosBusHoraire(idArret: String, idbus: String, date: String, heure: String, direction: Int) {
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        var myDirection: String = "0"
        val uriHoraire: Uri = Uri.parse(StarContract.StopTimes.CONTENT_URI.toString())
        if (direction == 0) {
            myDirection = "1"
        }
        val selectionArgs: Array<String>? =
            arrayOf<String>(idArret, idbus, date, heure, myDirection)
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
                listeHoraireBus.add(busStopTime)

            }
            c.close()

        }

    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "oncreate")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}