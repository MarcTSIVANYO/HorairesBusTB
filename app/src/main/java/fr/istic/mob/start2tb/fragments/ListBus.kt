package fr.istic.mob.start2tb.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.ClipData
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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import fr.istic.mob.horairesbustb.model.Route
import fr.istic.mob.start2tb.Data.ListBusData
import fr.istic.mob.start2tb.MyListenner
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.StarContract
import fr.istic.mob.start2tb.ViewModel.MyViewModel
import java.util.*
import kotlin.collections.ArrayList


class ListBus : Fragment() {

    private var SpinnerBus: Spinner? = null
    private var spinnerDirection: Spinner? = null
    private var btnDate: Button? = null
    private var btnTime: Button? = null
    private var btnValide: Button? = null
    private lateinit var textDate: TextView
    private lateinit var textTime: TextView
    private var myListBus: ArrayList<Route> = ArrayList<Route>()
    private lateinit var lbData: ListBusData
    private var selectedBus: Route? = null
    private lateinit var idBusSelected: String
    private lateinit var listDirections: ArrayList<String>
    private lateinit var busDirection: ArrayAdapter<String>
    private var mylistDirection: ArrayList<String> = ArrayList<String>()
    private lateinit var myListenner: MyListenner;
    private var minute: Int = 0
    private lateinit var dateTime: String
    private var position: Int = 0
    private var mytime: String = ""
    var mydate: String = ""
    var timeSetListener: OnTimeSetListener? = null
    lateinit var myViewModel: MyViewModel
    lateinit var item: ClipData.Item
    var direction: Int = 0
    var idRoute: String = ""
    var date: String = ""
    var heure: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_list_bus, container, false)

        btnDate = view.findViewById(R.id.btndate)
        btnTime = view.findViewById(R.id.btntime)
        btnValide = view.findViewById(R.id.btnValider)
        spinnerDirection = view.findViewById(R.id.spinnerDirection)
        SpinnerBus = view.findViewById(R.id.SpinnerBus)
        lbData = ListBusData(activity!!, 0, myListBus)

        SpinnerBus?.adapter = lbData

        SpinnerBus?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBus = myListBus.get(position)
                idBusSelected = selectedBus?.id.toString()
                mylistDirection = getDirection(selectedBus!!.longName.toString())
                busDirection = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, mylistDirection)
                busDirection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDirection?.adapter = busDirection
                spinnerDirection?.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        myCalendar()

        spinnerDirection?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (btnDate?.text.toString().equals("") || btnTime?.text.toString().equals("")) { } else {
                    busSelected(position, idBusSelected,btnDate?.text.toString(), btnTime?.text.toString())
                }
            }

        }

        return view
    }
    fun busSelected(direction: Int, id_route: String, date: String, time: String) {
        val arretBus: ArretBus = ArretBus(direction, id_route, date, heure)
        val fragmentManager: FragmentManager? = null
        fragmentManager?.beginTransaction()?.replace(R.id.frame, arretBus)?.addToBackStack("ArretBus")?.commit()
    }



    fun myCalendar() {
        val cal = Calendar.getInstance()
        val y: Int = cal.get(Calendar.YEAR)
        val m: Int = 1 + cal.get(Calendar.MONTH)
        val d: Int = cal.get(Calendar.DAY_OF_MONTH)
        val hh = cal.get(Calendar.HOUR_OF_DAY)
        val mm = cal.get(Calendar.MINUTE)

        btnDate?.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View?) {
                val datepickerdialog: DatePickerDialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    // var monthOfYear = monthOfYear + 1
                    var jours: String = dayOfMonth.toString()
                    var mois: String = monthOfYear.toString()
                    var year: String = year.toString()

                    if (monthOfYear < 10) {
                        mois = "0" + monthOfYear
                    }
                    if (dayOfMonth < 10) {
                        jours = "0" + dayOfMonth
                    }
                    //  mydate = year+ "/"+mois+ "/" +jours

                    btnDate!!.setText("" + jours + "/ " + mois + "/" + year)
                }, y, m, d)


                datepickerdialog.show()
            }
        });
        btnTime!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val timePickerDialog: TimePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    btnTime!!.setText("" + hourOfDay + ":" + minute);
                }, hh, mm, true)
                timePickerDialog.show()
            }

        })
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
        myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        if (savedInstanceState != null) {

            btnDate = activity?.findViewById(R.id.btndate)
            btnTime = activity?.findViewById(R.id.btntime)
            SpinnerBus = activity!!.findViewById(R.id.SpinnerBus)
            myListBus = savedInstanceState.getSerializable("Liste_Bus") as ArrayList<Route>
            SpinnerBus!!.setAdapter(this.lbData)

            spinnerDirection = activity!!.findViewById(R.id.spinnerDirection)
            mylistDirection =
                savedInstanceState.getSerializable("Liste_Direction") as ArrayList<String>
            busDirection = context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    mylistDirection
                )
            }!!
            spinnerDirection!!.setAdapter(busDirection)
            position = savedInstanceState.getInt("directionId")
            spinnerDirection!!.setSelection(position)
            mydate = savedInstanceState.getString("selectedDate").toString()
            textDate.setText(mydate)
            mytime = savedInstanceState.getString("selectedTime").toString()
            textTime.setText(mytime)
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
        val contentResolver = context?.contentResolver
        var c: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null
        val uriRoute: Uri = Uri.parse(StarContract.BusRoutes.CONTENT_URI.toString())
        val lastPath: String? = uriRoute.lastPathSegment
        c = contentResolver?.query(uriRoute, projection, selection, selectionArgs, sortOrder)
        if (c != null) {
            while (c.moveToNext()) {
                var busRoute: Route = Route(
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns._ID)), null,
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)), null,
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                    c.getString(c.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR)), null
                )
                myListBus.add(busRoute)
            }
            c.close()
            this.lbData?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            SpinnerBus!!.setAdapter(this.lbData)
        }
    }
}