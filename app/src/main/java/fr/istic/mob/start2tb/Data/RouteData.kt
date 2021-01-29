package fr.istic.mob.start2tb.Data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.istic.mob.horairesbustb.model.StopTime
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.start2tb.R

class RouteData(context: Context, resource: Int, list: ArrayList<StopTime>) : ArrayAdapter<StopTime>(context, resource, list)  {
     var stop: Stop? = null
    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
            if (convertView == null) {
                var convertView =
                    LayoutInflater.from(context).inflate(R.layout.un_horaire, parent, false)
            }

            var idhoraire: TextView? = convertView?.findViewById(R.id.idHoraire)
            var stopTime: StopTime? = getItem(position)

            if(stopTime!= null) {
                var text : String? = stop!!.name + " : " + stopTime.arrival
                if (idhoraire != null) {
                    idhoraire.setText(text)
                }
            }
         return convertView

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return  initView(position, convertView!!, parent)
        }
        override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {

            return initView(position, convertView, parent)

        }
    }