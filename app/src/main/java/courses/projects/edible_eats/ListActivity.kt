package courses.projects.edible_eats


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ListActivity : AppCompatActivity() {
    private var mlistView: ListView? = null
    private lateinit var restaurantName: TextView
    private lateinit var restaurantLocation: TextView
    private var menuChoicesList: ArrayList<String>? = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_list)

        mlistView = findViewById<View>(R.id.listView) as ListView

        val restaurant = intent.getStringExtra(RESTAURANT)

        restaurantName = findViewById(R.id.restName)
        restaurantName.text = restaurant

        menuChoicesList = intent.getStringArrayListExtra(MENU_CHOICES)
        val mAdapter = ArrayAdapter<String>(
            this@ListActivity,
            android.R.layout.simple_list_item_1,
            menuChoicesList!!
        )

        mlistView!!.adapter = mAdapter
    }

    companion object {
        const val MENU_CHOICES = "MenuChoice Names"
        const val RESTAURANT = "Restaurant Name"
        const val LOCATION = "Restaurant Location"
    }
}