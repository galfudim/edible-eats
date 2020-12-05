package courses.projects.edible_eats

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import java.util.*

class ListItemAdapter(
    context: Context,
    @LayoutRes private val layout: Int,
    private val list: List<String>)
    : ArrayAdapter<String>(context, layout, list), Filterable {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredList: List<String> = list
    private var mRestaurant: TextView? = null
    private var mLocation: TextView? = null
    private var updated: List<String> = arrayListOf<String>()

    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): String {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = inflater.inflate(layout, parent, false)
        mRestaurant = listItem.findViewById(R.id.restaurantName)
        mLocation = listItem.findViewById(R.id.location)

        val formattedEatery = getItem(position) as String
        val split = formattedEatery.split(":")

        mRestaurant!!.text = split[0]
        mLocation!!.text = split[1]

        return listItem
    }

     override fun getFilter(): Filter {
         return object : Filter() {
             override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                 filteredList = filterResults.values as List<String>
                 if (filterResults.count > 0) {
                     Log.d("FILTERED", filteredList.toString())
                     notifyDataSetChanged()
                     setDataChanged(filteredList)
                 }
             }

             override fun performFiltering(searchText: CharSequence): FilterResults {
                 val filteredResults = FilterResults()
                 if (searchText == null || searchText.isEmpty()) {
                     filteredResults.values = list
                     filteredResults.count = list.size
                 } else {

                     val count: Int = list.size
                     Log.d("COUNT", count.toString())
                     val newValues: ArrayList<String> = ArrayList<String>()

                     for (i in 0 until count) {
                         val value: String = list[i].split(":")[0]
                         val valueText: String = value.toLowerCase()

                         if (valueText.startsWith(searchText)) {
                             newValues.add(value)
                         } else {
                             val words = valueText.split(" ").toTypedArray()
                             for (word in words) {
                                 if (word.startsWith(searchText)) {
                                     newValues.add(value)
                                     break
                                 }
                             }
                         }
                     }

                     filteredResults.values = newValues
                     filteredResults.count = newValues.size
                 }

                 return filteredResults
             }
         }
     }

    fun getDataChanged(): List<String> {
        return updated
    }

    fun setDataChanged(results: List<String>) {
        updated = results
        Log.d("UPDATED", updated.toString())
    }

}