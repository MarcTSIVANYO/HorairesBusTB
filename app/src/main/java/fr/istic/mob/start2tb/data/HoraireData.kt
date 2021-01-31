package fr.istic.mob.start2tb.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.istic.mob.horairesbustb.model.StopTime
import fr.istic.mob.start2tb.R

class HoraireData(context: Context, resource: Int, list: ArrayList<StopTime>) : ArrayAdapter<StopTime>(context, resource, list)   {

    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
        if (convertView == null) {
            var convertView =
                LayoutInflater.from(context).inflate(R.layout.un_horaire, parent, false)
        }
        var idhoraire: TextView? = convertView?.findViewById(R.id.idHoraire)
        var stopTime: StopTime? = getItem(position)
        if(stopTime!= null){
            if (idhoraire != null) {
                idhoraire.setText(stopTime.arrival)
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