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


   class ListBusData(context: Context, resource: Int, list: ArrayList<Route>) : ArrayAdapter<Route>(context, resource, list) {


    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
     if (convertView == null) {
      var convertView = LayoutInflater.from(context).inflate(R.layout.un_bus, parent, false)
     }
     var lineNumber: TextView? = convertView?.findViewById(R.id.LineNumber)
     var busRoute: Route? = getItem(position)
     if(busRoute != null){
      if (lineNumber != null) {
       lineNumber.setText(busRoute.shortName)
       lineNumber.setTextColor(Color.parseColor("#" + busRoute.textColor))
       lineNumber.setBackgroundColor(Color.parseColor("#" + busRoute.color))

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


