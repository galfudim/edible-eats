package courses.projects.edible_eats


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class ListActivity : AppCompatActivity() {
    var listView: ListView? = null
     var restNames: ArrayList<String> =ArrayList<String>()
    var choicesList: ArrayList<String> =ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_list)

        listView = findViewById<View>(R.id.listView) as ListView
        val mBundle = intent.extras
        if (mBundle != null) {
            val rest = mBundle.getString("RestaurantName")
            val menuChoicesList = mBundle.getStringArrayList("MenuChoices")
            //restNames.add(rest!!)
            choicesList = menuChoicesList!!



        }
        val mAdapter =  ArrayAdapter<String>(this@ListActivity, android.R.layout.simple_list_item_1, choicesList)
        listView!!.adapter = mAdapter
    }
}