package fr.istic.mob.horairesbustb

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.system.Os
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import fr.istic.mob.horairesbustb.database.AppDatabase
import fr.istic.mob.horairesbustb.model.*
import fr.istic.mob.horairesbustb.services.Dezipper
import fr.istic.mob.horairesbustb.utils.DATABASE_NAME
import fr.istic.mob.horairesbustb.utils.URL
import fr.istic.mob.horairesbustb.utils.downloadTitle
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.BinaryHttpResponseHandler
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import fr.istic.mob.horairesbustb.model.*
import fr.istic.mob.horairesbustb.model.Calendar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity :  AppCompatActivity()  {

    var exportPath:String =""
   // var progress: LinearLayout? = findViewById<LinearLayout>(R.id.progress)
    private lateinit var download_btn: Button
    private val folder ="starBus/"
    var mydownloadFolderid:Long =0

    private var progressBar: ProgressBar? = null

    private var linearLayout: LinearLayout? = null
    private var i = 0
    private val handler = Handler()
    private var txtView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        download_btn = findViewById(R.id.download_btn)

        download_btn.setOnClickListener{
            linearLayout=findViewById<View>(R.id.progressLinear) as LinearLayout
            linearLayout!!.visibility = View.VISIBLE
            download_btn.visibility=View.GONE

            beginDownload()

            //linearLayout!!.visibility = View.GONE
            //Toast.makeText(this@MainActivity, "Its toast!", Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null) {

        }
    }

    fun beginDownload(){
        val mydownloadFolder = File(
            applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            folder
        )
        if (!mydownloadFolder.exists()){
            mydownloadFolder.mkdir();
        }

        var uri = Uri.parse(URL)
        var request :DownloadManager.Request=DownloadManager.Request(Uri.parse(URL))
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
            downloadPathArray[5] + ".json"
        request.setDestinationInExternalFilesDir(
            applicationContext,
            Environment.DIRECTORY_DOWNLOADS + "/" + folder,
            path
        ) // Storage directory path
        var dm:DownloadManager=getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
        //dezipperFile(mydownloadFolder.absolutePath+"/"+path)
        getJsonDataFromAsset(mydownloadFolder.absolutePath + "/" + path)
        //dezipperFile(mydownloadFolder.absolutePath+"/"+path)
    }

    fun doawnload(){
        //startService( Intent(this, DownloadManagerService::class.java))

        var client:AsyncHttpClient = AsyncHttpClient()
        client.addHeader("mimetype", "application/octect-stream")
        client[URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONArray?
            ) {
                try {
                    var record: JSONArray? = response
                    var js: JSONObject = response!!.optJSONObject(0).getJSONObject("fields")
                    var url: String = js.getString("url")
                    getJsonDataFromAsset(url)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }]
    }
    fun getJsonDataFromAsset(fileName: String) {
        val url = BufferedReader(FileReader(File(fileName)))

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
        val  dateFormat:SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd");
        val dateToday =  dateFormat.parse(dateFormat.format(Date()))
        version.forEach {
            val debut = dateFormat.parse(""+it.field.dateDebut)
            val fin = dateFormat.parse(""+it.field.dateFin)
            if (dateToday>=debut && dateToday<=fin)
            {
                currentUrl =it.field.url
            }
        }
        dezipperFile(currentUrl)

    }
    fun dezipperFile(url: String){
        var client:AsyncHttpClient = AsyncHttpClient()
        val type = arrayOf(
            "application/octect-stream"
        )
        client.get(url, object : BinaryHttpResponseHandler(type) {
            override fun onStart() {
                super.onStart()
                loaderBar(true)
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                binaryData: ByteArray?
            ) {
                try {
                    val name: String = url.substring(url.lastIndexOf("/") + 1, url.length)

                    val dirFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), folder)
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

                    var dzip = Dezipper(file.absolutePath, exportPath)
                    dzip.unzip()
                    InsertDataToDatabase()
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
                ).show();
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



    private fun InsertDataToDatabase(){
        //Log.d("String", "String")
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DATABASE_NAME
        ).build()


        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
       //Insert into trips
        val tripFile = File(storageDir, "trips.txt")
        val tripReader = BufferedReader(FileReader(tripFile))
        var tripCurrentLine = tripReader.readLine();
        val trips: MutableSet<Trip> = mutableSetOf()
        while (tripCurrentLine !=null){
            val values = tripCurrentLine.split(",").toTypedArray()
            trips.add(
                Trip(
                    values[0].toInt(),
                    values[1].toInt(),
                    values[2].toInt(),
                    values[3].toString(),
                    values[4].toString(),
                    values[5].toString(),
                    values[6].toString(),
                    values[7].toString(),
                    values[8].toString(),
                    values[9].toString()
                )
            );
        }
        db.tripDao().addAllTrips(trips);
        //End insert into trips

        //Insert into routes
        val routeFile = File(storageDir, "routes.txt")
        val routeReader = BufferedReader(FileReader(routeFile))
        var routeCurrentLine = routeReader.readLine();
        val routes: MutableSet<Route> = mutableSetOf();
        while (routeCurrentLine !=null){
            val values = routeCurrentLine.split(",").toTypedArray()
            routes.add(
                Route(
                    values[0].toInt(),
                    values[1].toInt(),
                    values[2].toString(),
                    values[3].toString(),
                    values[4].toString(),
                    values[5].toString(),
                    values[6].toString(),
                    values[7].toString(),
                    values[8].toString(),
                    values[9].toString()
                )
            );
        }
        db.routeDao().addAllRoutes(routes);
        //End insert into routes

        //Insert into stops
        val stopFile = File(storageDir, "stops.txt")
        val stopReader = BufferedReader(FileReader(routeFile))
        var stopCurrentLine = routeReader.readLine();
        val stops: MutableSet<Stop> = mutableSetOf();
        while (stopCurrentLine !=null){
            val values = stopCurrentLine.split(",").toTypedArray()
            stops.add(
                Stop(
                    values[0].toInt(),
                    values[1].toString(),
                    values[2].toString(),
                    values[3].toString(),
                    values[4].toString(),
                    values[5].toString(),
                    values[6].toString(),
                    values[7].toString(),
                    values[8].toString(),
                    values[9].toString(),
                    values[10].toString(),
                    values[11].toString()
                )
            );
        }
        db.stopDao().addAllStops(stops);
        //End insert into stops

        //Insert into stopTimes
        val stopTimesFile = File(storageDir, "stop_times.txt")
        val stopTimeReader = BufferedReader(FileReader(routeFile))
        var stopTimeCurrentLine = routeReader.readLine();
        val stopTimes: MutableSet<StopTime> = mutableSetOf();
        while (stopTimeCurrentLine !=null){
            val values = stopTimeCurrentLine.split(",").toTypedArray()
            stopTimes.add(
                StopTime(
                    values[0].toInt(),
                    values[1].toInt(),
                    values[2].toInt(),
                    values[3].toInt(),
                    values[4].toInt(),
                    values[5].toString(),
                    values[6].toString(),
                    values[7].toString(),
                    values[8].toString(),
                    values[9].toString()
                )
            );
        }
        db.stopTimeDao().addAllStopTimes(stopTimes);
        //End insert into stopTimes


        //Insert into calendars
        val calendarTimesFile = File(storageDir, "calendar.txt")
        val calendarReader = BufferedReader(FileReader(routeFile))
        var calendarCurrentLine = routeReader.readLine();
        val calendars: MutableSet<Calendar> = mutableSetOf();
        while (calendarCurrentLine !=null){
            val values = calendarCurrentLine.split(",").toTypedArray()
            calendars.add(
                Calendar(
                    values[0].toInt(),
                    values[1].toString(),
                    values[2].toString(),
                    values[3].toString(),
                    values[4].toString(),
                    values[5].toString(),
                    values[6].toString(),
                    values[7].toString(),
                    values[8].toString()
                )
            );
        }
        db.calendarDao().addAllCalendars(calendars);
        //End insert into calendars
    }
}

