package fr.istic.mob.star2tb

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.istic.mob.star2tb.fragements.StopTimesFragment
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.route_fragment)

        // Calendars
        var date: Calendar = Calendar.getInstance()
        var thisAYear = date.get(Calendar.YEAR)
        var thisAMonth = date.get(Calendar.MONTH)
        var thisADay = date.get(Calendar.DAY_OF_MONTH)
        val btnDatePicker=findViewById(R.id.date_picker) as Button;
        val dateText=findViewById(R.id.dateText) as TextView;

        btnDatePicker.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
                    // Display Selected date in textbox
                    thisAMonth = thisMonth + 1
                    thisADay = thisDay
                    thisAYear = thisYear

                    dateText.setText("" + thisDay + "/" + thisAMonth + "/" + thisYear)
                    val newDate: Calendar = Calendar.getInstance()
                    newDate.set(thisYear, thisMonth, thisDay)
                },
                thisAYear,
                thisAMonth,
                thisADay
            )
            dpd.show()

        }

        val mPickTimeBtn = findViewById<Button>(R.id.pickTimeBtn)
        val TimetextView     = findViewById<TextView>(R.id.timeTv)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                TimetextView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }


        val btnSubmit = findViewById<Button>(R.id.btnValider)
        btnSubmit.setOnClickListener {
            val firstFragment = StopTimesFragment()

            if(savedInstanceState == null) { // initial transaction should be wrapped like this
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, firstFragment) 
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}