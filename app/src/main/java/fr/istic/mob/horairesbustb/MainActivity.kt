package fr.istic.mob.horairesbustb

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.system.Os
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.BinaryHttpResponseHandler
import cz.msebera.android.httpclient.Header
import fr.istic.mob.horairesbustb.database.AppDatabase
import fr.istic.mob.horairesbustb.model.*
import fr.istic.mob.horairesbustb.model.Calendar
import fr.istic.mob.horairesbustb.services.Dezipper
import fr.istic.mob.horairesbustb.utils.DATABASE_NAME
import fr.istic.mob.horairesbustb.utils.URL
import fr.istic.mob.horairesbustb.utils.downloadTitle
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity :  AppCompatActivity()  {

    var exportPath:String =""
   // var progress: LinearLayout? = findViewById<LinearLayout>(R.id.progress)
    private lateinit var download_btn: Button
    private val folder ="starBus/"
    private var linearLayout: LinearLayout? = null
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
        setContentView(R.layout.activity_main)
        download_btn = findViewById(R.id.download_btn)

        download_btn.setOnClickListener{
            linearLayout=findViewById<View>(R.id.progressLinear) as LinearLayout
            linearLayout!!.visibility = View.VISIBLE
            download_btn.visibility=View.GONE

            beginDownload()

        }

       /* if (savedInstanceState != null) {

        }*/
    }

    fun test(){
        var cursor: Cursor? = null
        val projection: Array<String>? = null
        val selection: String? = null
        val selectionArgs: Array<String>? = null
        val sortOrder: String? = null
        val uriRoute: Uri = Uri.parse(StarContract.BusRoutes.CONTENT_URI.toString())
        val lastPath: String? = uriRoute.lastPathSegment
        cursor = contentResolver?.query(uriRoute, projection, selection, selectionArgs, sortOrder)

        if(cursor!=null && cursor.moveToFirst()){
               do {
                   var name = cursor?.getString(cursor?.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME))
                    Log.d("test : ", "message :"+name)
               }while (cursor.moveToNext())
        }
        cursor?.close()

        val selectionArgs2=arrayOf("0001")
        cursor = contentResolver?.query(uriRoute, projection, selection, selectionArgs2, sortOrder)
        //val cursor2 = contentResolver.query(uriRoute, projection, selection, selectionArgs, sortOrder)
        if(cursor!=null && cursor.moveToFirst()){
            do {
                var name = cursor?.getString(cursor?.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME))
                Log.d("test : ", "long name : "+name)
            }while (cursor.moveToNext())
        }
        cursor?.close()

        var selectionArgs3=arrayOf("0001","1")
        val uriStop: Uri = Uri.parse(StarContract.Stops.CONTENT_URI.toString())
        cursor = contentResolver?.query(uriStop, projection, selection, selectionArgs3, sortOrder)
        if(cursor!=null && cursor.moveToFirst()){
            do {
                var name = cursor?.getString(cursor?.getColumnIndex(StarContract.Stops.StopColumns.NAME))
                Log.d("test : ", " Stop name :"+name)
            }while (cursor.moveToNext())
        }
        cursor?.close()
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun beginDownload(){
        val mydownloadFolder = File(
                applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                folder
        )
        if (!mydownloadFolder.exists()){
            mydownloadFolder.mkdir()
        }

        val uri = Uri.parse(URL)
        val request :DownloadManager.Request=DownloadManager.Request(Uri.parse(URL))
                .setTitle(downloadTitle)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .addRequestHeader("Content-type", "application/octect-stream")
                .setMimeType("application/octet-stream")
        val downloadPathArray = URL.split("/")
        val path= if (uri.lastPathSegment != null && uri.lastPathSegment!!.endsWith(".zip"))
            uri.lastPathSegment
        else
            downloadPathArray[5].replace("\"", "") + ".json"
        request.setDestinationInExternalFilesDir(
                applicationContext,
                Environment.DIRECTORY_DOCUMENTS + "/" + folder,
                path
        ) // Storage directory path

        //Log.d("tets",  ""+path)
        val dm:DownloadManager=getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
        getJsonDataFromAsset(mydownloadFolder.absolutePath + "/" + path)
        //getJsonDataFromAsset(applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + folder + "/" + path)
    }
/*
    fun doawnload(){
        val client:AsyncHttpClient = AsyncHttpClient()
        client.addHeader("mimetype", "application/octect-stream")
        client[URL, object : JsonHttpResponseHandler() {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray?
            ) {
                try {
                    val js: JSONObject = response!!.optJSONObject(0).getJSONObject("fields")
                    val url: String = js.getString("url")
                    getJsonDataFromAsset(url)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }].replace("\"", "")
    }
    */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getJsonDataFromAsset(fileName: String) {
        //Log.d("tets",  ""+fileName)
        val file = File(
                fileName
        )
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        val response = stringBuilder.toString()
        var currentUrl:String=""
        val version = Gson().fromJson(response, Array<Version>::class.java)
        val  dateFormat:SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd")
        val dateToday: Date =  dateFormat.parse(dateFormat.format(Date()))
        version.forEach {
            val debut = dateFormat.parse("" + it.field.dateDebut)
            val fin = dateFormat.parse("" + it.field.dateFin)
            if (dateToday>=debut && dateToday<=fin)
            {
                currentUrl =it.field.url
            }
        }
        dezipperFile(currentUrl)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun dezipperFile(url: String){
        val client:AsyncHttpClient = AsyncHttpClient()
        val type = arrayOf(
                "application/octect-stream"
        )
        client.get(url, object : BinaryHttpResponseHandler(type) {
            override fun onStart() {
                super.onStart()
                loaderBar(true)
            }

            override  fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    binaryData: ByteArray?
            ) {
                try {
                    val name: String = url.substring(url.lastIndexOf("/") + 1, url.length)

                    val dirFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), folder)
                    if (!dirFile.exists()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Os.mkdir(dirFile.absolutePath, 0)
                        }
                    }

                    val file = File(dirFile, name)
                    val stream = FileOutputStream(file)
                    stream.write(binaryData)
                    stream.close()

                    exportPath = file.absolutePath
                    exportPath = exportPath.replace(".zip", "")
                    exportPath = "$exportPath/"

                    val dzip = Dezipper(file.absolutePath, exportPath)
                    dzip.unzip()
                    insertIntoDatabase(exportPath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    binaryData: ByteArray?,
                    error: Throwable?
            ) {
                Toast.makeText(
                        this@MainActivity,
                        "Echec de synchronisation de donnée",
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFinish() {
                super.onFinish()
                loaderBar(false)
            }
        })
    }

    private fun loaderBar(b: Boolean) {
        when (b) {
            true -> linearLayout!!.visibility = View.VISIBLE
            false -> linearLayout!!.visibility = View.GONE
        }
    }

    private  fun insertIntoDatabase(path:String){
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom(1,2,3,4)
            .build()

        //Insert into calendars
        val calendarTimesFile = File(path, "calendar.txt")
        val calendarReader = BufferedReader(FileReader(calendarTimesFile))
        var calendarCurrentLine = calendarReader.readLine()
        val calendars: MutableSet<Calendar> = mutableSetOf()
        var iCalend :Int=0
        while (calendarCurrentLine !=null){ 
            if(iCalend==0){ 
                calendarCurrentLine = calendarReader.readLine()
            }
            val values = calendarCurrentLine.split(",").toTypedArray()
 
            calendars.add(
                Calendar(
                    values[0].replace("\"", "").replace("\"", ""),
                    values[1].replace("\"", "").replace("\"", ""),
                    values[2].replace("\"", "").replace("\"", ""),
                    values[3].replace("\"", "").replace("\"", ""),
                    values[4].replace("\"", "").replace("\"", ""),
                    values[5].replace("\"", "").replace("\"", ""),
                    values[6].replace("\"", "").replace("\"", ""),
                    values[7].replace("\"", "").replace("\"", ""),
                    values[8].replace("\"", "").replace("\"", "")
                )
            )
            calendarCurrentLine = calendarReader.readLine()
            iCalend=1
        }
        db.calendarDao().addAllCalendars(calendars)
        //End insert into calendars

        //Insert into routes
        val routeFile = File(path, "routes.txt")
        val routeReader = BufferedReader(FileReader(routeFile))
        var routeCurrentLine = routeReader.readLine()
        val routes: MutableSet<Route> = mutableSetOf()
        var iRoute:Int=0
        while (routeCurrentLine !=null){
            if(iRoute==0){
                routeCurrentLine = routeReader.readLine()
            }
            val values = routeCurrentLine.split(",").toTypedArray()
            routes.add(
                Route(
                    values[0].replace("\"", ""),
                    values[1].replace("\"", ""),
                    values[2].replace("\"", ""),
                    values[3].replace("\"", ""),
                    values[4].replace("\"", ""),
                    values[5].replace("\"", ""),
                    values[6].replace("\"", ""),
                    values[7].replace("\"", ""),
                    values[8].replace("\"", ""),
                    values[9].replace("\"", "")
                )
            )
            routeCurrentLine=routeReader.readLine()
            iRoute=1
        }
        db.routeDao().addAllRoutes(routes)
        //End insert into routes

        //Insert into stops
        val stopFile = File(path, "stops.txt")
        val stopReader = BufferedReader(FileReader(stopFile))
        var stopCurrentLine = stopReader.readLine()
        val stops: MutableSet<Stop> = mutableSetOf()
        var iStop:Int=0
        while (stopCurrentLine !=null){
            if(iStop==0){
                stopCurrentLine = stopReader.readLine()
            }
            val values = stopCurrentLine.split(",").toTypedArray()
            stops.add(
                Stop(
                    values[0].replace("\"", ""),
                    values[1].replace("\"", ""),
                    values[2].replace("\"", ""),
                    values[3].replace("\"", ""),
                    values[4].replace("\"", ""),
                    values[5].replace("\"", ""),
                    values[6].replace("\"", ""),
                    values[7].replace("\"", ""),
                    values[8].replace("\"", ""),
                    values[9].replace("\"", ""),
                    values[10].replace("\"", ""),
                    values[11].replace("\"", "")
                )
            )
            stopCurrentLine = stopReader.readLine()
            iStop=1
        }
        db.stopDao().addAllStops(stops)
        //End insert into stops

        //Insert into trips
        var trip: Trip
        var iTrip:Int=0
        File(path, "trips.txt").useLines { lines -> lines.forEach {
            if(iTrip>0){
                val values = it.split(",").toTypedArray()
                trip= Trip(
                    routeId = values[0].replace("\"", ""),
                    serviceId = values[1].replace("\"", ""),
                    id = values[2].replace("\"", ""),
                    headSign = values[3].replace("\"", ""),
                    shortName = values[4].replace("\"", ""),
                    directionId = values[5].replace("\"", ""),
                    blockId = values[6].replace("\"", ""),
                    shapeId = values[7].replace("\"", ""),
                    WHEELCHAIR_ACCESSIBLE = values[8].replace("\"", ""),
                    bikesAllowed = values[9].replace("\"", "")
                )
                db.tripDao().addTrip(trip)
            }
            iTrip=1
        }
        //End insert into trips

        //Insert into stopTimes
        var stopTime: StopTime
        var istopTime:Int=0
        File(path, "stop_times.txt").useLines { lines -> lines.forEach { it ->
            if (istopTime > 0) {
                val values = it.split(",").toTypedArray()
                stopTime = StopTime(
                        0,
                        values[0].replace("\"", ""),
                        values[1].replace("\"", ""),
                        values[2].replace("\"", ""),
                        values[3].replace("\"", ""),
                        values[4].replace("\"", ""),
                        values[5].replace("\"", ""),
                        values[6].replace("\"", ""),
                        values[7].replace("\"", ""),
                        values[8].replace("\"", "")
                )
                db.stopTimeDao().addStopTime(stopTime)
            }
            istopTime=1
        }
        }
        //End insert into stopTimes
        linearLayout!!.visibility = View.GONE
        Toast.makeText(this@MainActivity, "Opération effectué avec succès!", Toast.LENGTH_SHORT).show()
     
    }

    }
}

