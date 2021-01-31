package fr.istic.mob.start2tb.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.istic.mob.horairesbustb.model.Stop
import fr.istic.mob.start2tb.R

class StopData(context: Context, resource: Int, list: ArrayList<Stop>) :
    ArrayAdapter<Stop>(context, resource, list) {

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutView: View
        val viewHolder: MyViewHolder

        if (convertView == null) {
            layoutView = LayoutInflater.from(context).inflate(R.layout.un_arret, parent, false)
            viewHolder = MyViewHolder(layoutView)
            layoutView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as MyViewHolder
            layoutView = convertView
        }

        val stop: Stop? = getItem(position)
        viewHolder.txt.setText(stop?.name)
        return layoutView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)

    }

    private class MyViewHolder(view: View) {
        val txt: TextView = view.findViewById(R.id.idArret)
    }
}