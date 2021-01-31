package fr.istic.mob.start2tb.Data

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.istic.mob.horairesbustb.model.Route
import fr.istic.mob.start2tb.R


class ListBusData(context: Context, resource: Int, list: ArrayList<Route>) :
    ArrayAdapter<Route>(context, resource, list) {


    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutView: View
        var viewHolder: MyViewHolder

        if (convertView == null) {
            layoutView = LayoutInflater.from(context).inflate(R.layout.un_bus, parent, false)
            viewHolder= MyViewHolder(layoutView)
            layoutView.tag=viewHolder
        } else {
            viewHolder=convertView.tag as MyViewHolder
            layoutView = convertView
        }

        val busRoute: Route? = getItem(position)
        viewHolder.txt.setText(busRoute?.shortName)
        viewHolder.txt.setTextColor(Color.parseColor("#" + busRoute?.textColor))
        viewHolder.txt.setBackgroundColor(Color.parseColor("#" + busRoute?.color))
        return layoutView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)

    }

    class MyViewHolder(view :View) {
        val txt:TextView=view.findViewById(R.id.LineNumber)
    }
}



