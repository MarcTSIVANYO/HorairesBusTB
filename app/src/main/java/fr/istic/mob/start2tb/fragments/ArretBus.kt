package fr.istic.mob.start2tb.fragments

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.start2tb.Data.StopData
import fr.istic.mob.start2tb.MainActivity
import fr.istic.mob.start2tb.MyListenner
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract
import fr.istic.mob.start2tb.ViewModel.MyViewModel


class ArretBus(direction: Int, id_route: String, date: String, heure: String) : Fragment() {

    private lateinit var textView: TextView
    private lateinit var mActivite: MainActivity
    private lateinit var myFragmentListnner: MyListenner
    private var listeArret: ArrayList<Stop> =  ArrayList<Stop>()
    private lateinit var sData: StopData
    private lateinit var listView: ListView
    private  var dir: Int = 0
    private lateinit var idRoute: String
    private lateinit var date: String
    private lateinit var time: String
    lateinit var myViewModel : MyViewModel
    private  var TAG: String = "TITLE"
    private lateinit var data: ArrayAdapter<Stop>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_arret_bus, container, false)
        this.listView = view.findViewById(R.id.idArrets)
        chargerInfosArret(this.dir, this.idRoute)
        data = StopData(context!!, 0, listeArret)
        this.listView.setAdapter(data)
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val selectedStop : Stop  = listeArret?.get(position)
            val idStop : String = selectedStop.id.toString()
            arretSelected(idStop, idRoute,dir, date,time)
        }

        return view
    }
    fun arretSelected(id_arret: String, id_bus: String, dir: Int, date: String, time: String) {
        var horaireBus: HoraireBus = HoraireBus(id_arret, id_bus, dir, date, time)
        val fragmentManager: FragmentManager? = null
        fragmentManager?.beginTransaction()?.replace(R.id.frame, horaireBus, "horaireBus")?.addToBackStack("horaireBus")?.commit()
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        if( savedInstanceState != null ){
            listeArret = savedInstanceState.getSerializable("Liste_Arrêts") as ArrayList<Stop>
            this.listView = activity?.findViewById(R.id.idArrets)!!
            data= StopData(context!!, 0, listeArret)
            this.listView.setAdapter(data)


        }

    }

    fun chargerInfosArret(dir: Int, idRoute: String) {
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        val uriStop: Uri = Uri.parse(StarContract.Stops.CONTENT_URI.toString())
        val lastPath: String? = uriStop.lastPathSegment
        var dirStrg = Integer.toString(dir)
        var selectionArgs = arrayOf<String>(idRoute, dirStrg)

        c = activity?.managedQuery(uriStop, projection, selection, selectionArgs, sortOrder)
        if (c != null) {
            while (c.moveToNext()) {
                var busStops: Stop = Stop(
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns._ID)),
                    null,
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.DESCRIPTION)),
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.LATITUDE)),
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.LONGITUDE)),
                    null,
                    null,
                    null,
                    null,
                    null,
                    c.getString(c.getColumnIndex(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING))

                )
                listeArret.add(busStops)
                data.notifyDataSetChanged()
            }
            c.close()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("Liste_Arrêts", listeArret)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "oncreate")
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
