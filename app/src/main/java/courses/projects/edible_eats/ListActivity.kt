package courses.projects.edible_eats


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ListActivity : AppCompatActivity() {
    var listView: ListView? = null
    lateinit var restName : TextView
    var restNames: ArrayList<String> = ArrayList<String>()
    var menuChoicesList: ArrayList<String>? = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_list)

        listView = findViewById<View>(R.id.listView) as ListView
        val mBundle = intent.extras
        val rest = intent.getStringExtra("RestaurantName")

        restName = findViewById(R.id.restName)
        restName.text = rest

        menuChoicesList = intent.getStringArrayListExtra("MenuChoice Names")
        val mAdapter = ArrayAdapter<String>(
            this@ListActivity,
            android.R.layout.simple_list_item_1,
            menuChoicesList!!
        )
        listView!!.adapter = mAdapter
    }
}