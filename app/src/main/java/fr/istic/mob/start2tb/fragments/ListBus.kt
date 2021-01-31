@file:Suppress("unused")

package fr.istic.mob.start2tb.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import fr.istic.mob.horairesbustb.model.Route
import fr.istic.mob.start2tb.data.ListBusData
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract
import java.util.*
import kotlin.collections.ArrayList


class ListBus : Fragment() {

    private var spinnerBus: Spinner? = null
    private var spinnerDirection: Spinner? = null
    private var btnDate: Button? = null
    private var btnTime: Button? = null
    private var textDate: TextView? = null
    private var textTime: TextView? = null
    private var btnValid: Button? = null
    private var myListBus: ArrayList<Route> = ArrayList<Route>()
    private lateinit var lbData: ListBusData
    private var selectedBus: Route? = null
    private lateinit var idBusSelected: String
    private lateinit var listDirections: ArrayList<String>
    private lateinit var busDirection: ArrayAdapter<String>
    private var mylistDirection: ArrayList<String> = ArrayList<String>()
    private var position: Int = 0
    private var mytime: String = ""
    var mydate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_list_bus, container, false)

        btnDate = view.findViewById(R.id.btndate)
        btnTime = view.findViewById(R.id.btntime)
        textDate = view.findViewById(R.id.dateText)
        textTime = view.findViewById(R.id.timeText)
        btnValid = view.findViewById(R.id.btntValid)
        spinnerDirection = view.findViewById(R.id.spinnerDirection)
        spinnerBus = view.findViewById(R.id.spinnerBus)
        lbData = ListBusData(activity!!, 0, myListBus)
        spinnerBus?.setAdapter(lbData)

        spinnerBus?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBus = myListBus.get(position)
                idBusSelected = selectedBus?.id.toString()
                mylistDirection = getDirection(selectedBus!!.longName.toString())
                busDirection = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, mylistDirection)
                busDirection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDirection?.setAdapter(busDirection)
                spinnerDirection?.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        val cal = Calendar.getInstance(Locale.FRANCE)
        val y: Int = cal.get(Calendar.YEAR)
        val m: Int = cal.get(Calendar.MONTH)
        val d: Int = cal.get(Calendar.DAY_OF_MONTH)
        val hh = cal.get(Calendar.HOUR_OF_DAY)
        val mm = cal.get(Calendar.MINUTE)

        btnDate?.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View?) {

                val datepickerdialog: DatePickerDialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var jours: String = dayOfMonth.toString()
                    val moisCourant = monthOfYear + 1
                    var mois: String = moisCourant.toString()
                    val annee: String = year.toString()
                    if (moisCourant < 10) {
                        mois = "0" + moisCourant
                    }
                    if (dayOfMonth < 10) {
                        jours = "0" + dayOfMonth
                    }
                    textDate?.setText( jours + "/"+ mois + "/" + annee) }, y, m, d)


                datepickerdialog.show()
            }
        });
        btnTime!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val timePickerDialog: TimePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    textTime?.setText("" + hourOfDay + ":" + minute);
                }, hh, mm, true)

                timePickerDialog.show()
            }

        })

        spinnerDirection?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onNothingSelected(parent: AdapterView<*>?) {}
             override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                  if (textDate?.text.toString().equals("") || textDate?.text.toString() == null || textTime?.text.toString().equals("") || textTime?.text.toString() == null) {

                  }
                  else {
                       busSelected(position, idBusSelected, textDate?.text.toString(), textTime?.text.toString())
                   }
               }

           }

        return view
    }

    fun busSelected(direction: Int, id_route: String, date: String, time: String) {
        val arretBus: ArretBus = ArretBus(direction, id_route, date, time)
        val fg: Fragment? = arretBus.newInstance()
        if (fg != null) {
            fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit).replace(R.id.frame, arretBus).addToBackStack("ArretBus").commit()
        }
    }

    //retourne la liste des directions
    private fun getDirection(directions: String): ArrayList<String> {
        var dir: List<String> = directions.split("<>")
        listDirections = ArrayList<String>()
        listDirections.add(dir[0])
        listDirections.add(dir[dir.lastIndex])
        return listDirections
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListBus().apply {
                    arguments = Bundle().apply {
                        //  putString(ARG_PARAM1, param1)
                        //putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     //   myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        if (savedInstanceState != null) {
            textDate = activity?.findViewById(R.id.dateText)
            textTime = activity?.findViewById(R.id.timeText)
            btnDate = activity?.findViewById(R.id.btndate)
            btnTime = activity?.findViewById(R.id.btntime)
            spinnerBus = activity?.findViewById(R.id.spinnerBus)
            myListBus = savedInstanceState.getSerializable("Liste_Bus") as ArrayList<Route>
            spinnerBus?.setAdapter(this.lbData)

            spinnerDirection = activity?.findViewById(R.id.spinnerDirection)
            mylistDirection = savedInstanceState.getSerializable("Liste_Direction") as ArrayList<String>
            busDirection = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, mylistDirection)
            spinnerDirection!!.setAdapter(busDirection)
            position = savedInstanceState.getInt("directionId")
            spinnerDirection!!.setSelection(position)
            mydate = savedInstanceState.getString("selectedDate").toString()
           /// textDate.setText(mydate)
            mytime = savedInstanceState.getString("selectedTime").toString()
            //textTime.setText(mytime)
        } else {
            chargerInfosBus()
        }

    }

    //Sauvegarde des données avec les objets serialisable

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("Liste_bus", myListBus)
        outState.putSerializable("Liste_direction", mylistDirection)
        outState.putSerializable("Date_selected", mydate)
        outState.putSerializable("Liste_bus", mytime)
        outState.putSerializable("Direction_id", position)


    }
    //recuperation des données de Routebus à partir du fournisseur de contenu

    fun chargerInfosBus() {
        var cursor: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null
        val uriRoute: Uri = Uri.parse(StarContract.BusRoutes.CONTENT_URI.toString())
        cursor = activity?.managedQuery(uriRoute, projection, selection, selectionArgs, sortOrder)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var busRoute: Route = Route(
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID)), null,
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)), null,
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR)), null

                )
                myListBus.add(busRoute)

            }
            cursor.close()
            this.lbData?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBus!!.setAdapter(this.lbData)
        }
    }
}


