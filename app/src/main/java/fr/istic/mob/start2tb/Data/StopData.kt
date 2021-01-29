package fr.istic.mob.start2tb.Data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.start2tb.R
import fr.istic.mob.start2tb.fragments.ArretBus

class StopData(context: Context, resource: Int, list: ArrayList<Stop>) : ArrayAdapter<Stop>(context, resource, list)  {

        private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
            if (convertView == null) {
                var convertView =
                    LayoutInflater.from(context).inflate(R.layout.un_arret, parent, false)
            }
            var idArret: TextView? = convertView?.findViewById(R.id.idArret)
            var stop: Stop? = getItem(position)
            if (stop != null) {
                if (idArret != null) {
                    idArret.setText(stop.name)
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