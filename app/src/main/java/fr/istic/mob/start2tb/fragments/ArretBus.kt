package fr.istic.mob.start2tb.fragments

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.start2tb.data.StopData
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract


class ArretBus(val dir: Int, val idRoute: String, val date: String, val time: String) : Fragment() {

    private var listeArret: ArrayList<Stop> = ArrayList<Stop>()
    private lateinit var listView: ListView
    private var TAG: String = "TITLE"
    private lateinit var data: ArrayAdapter<Stop>
    private var btnValid: Button? = null
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_arret_bus, container, false)
        this.listView = view.findViewById(R.id.idArrets)
        btnValid = view.findViewById(R.id.btntValid)
        data = StopData(mContext, 0, listeArret)
        chargerInfosArret(dir, idRoute)
        this.listView.setAdapter(data)
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val selectedStop: Stop = listeArret?.get(position)
            val idStop: String = selectedStop.id.toString()
            arretSelected(idStop, idRoute, dir, date, time)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    fun arretSelected(id_arret: String, id_bus: String, dir: Int, date: String, time: String) {
        val horaireBus: HoraireBus = HoraireBus(id_arret, id_bus, dir, date, time)
        val fg: Fragment? = horaireBus.newInstance()
        if (fg != null) {
            fragmentManager!!.beginTransaction()?.setCustomAnimations(R.anim.enter, R.anim.exit)
                .replace(R.id.frame, horaireBus).addToBackStack("ArretBus").commit()
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            listeArret = savedInstanceState.getSerializable("Liste_Arrêts") as ArrayList<Stop>
            this.listView = activity?.findViewById(R.id.idArrets)!!
            data = StopData(context!!, 0, listeArret)
            this.listView.setAdapter(data)
        }
    }

    fun newInstance(): Fragment? {
        val mFrgment = ArretBus(dir, idRoute, date, time)
        return mFrgment
    }

    fun chargerInfosArret(dir: Int, idRoute: String) {

        var cursor: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val sortOrder: String? = null
        val uriStop: Uri = Uri.parse(StarContract.Stops.CONTENT_URI.toString())
        val dirStrg = Integer.toString(dir)
        val selectionArgs = arrayOf<String>(idRoute, dirStrg)

        cursor = activity?.managedQuery(uriStop, projection, selection, selectionArgs, sortOrder)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val busStops: Stop = Stop(
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.STOP_ID)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.LONGITUDE))

                )
                listeArret.add(busStops)
                data.notifyDataSetChanged()
                Log.d("test : ", "Bus name : " + busStops.name)
            }
            cursor.close()
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
