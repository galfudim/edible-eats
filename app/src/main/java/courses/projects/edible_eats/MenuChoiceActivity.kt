package courses.projects.edible_eats


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuChoiceActivity : AppCompatActivity() {
    private var mlistView: ListView? = null
    private lateinit var mRestaurantName: TextView
    private var mAdapter: ArrayAdapter<String>? = null
    private var menuChoicesList: ArrayList<String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_choice)

        mlistView = findViewById<View>(R.id.listView) as ListView
        mRestaurantName = findViewById(R.id.restName)

        val restaurant = intent.getStringExtra(RESTAURANT)
        mRestaurantName.text = restaurant

        menuChoicesList = intent.getStringArrayListExtra(MENU_CHOICES)
        mAdapter = ArrayAdapter<String>(
            this@MenuChoiceActivity,
            R.layout.custom_list_item,
            menuChoicesList!!
        )

        mlistView!!.adapter = mAdapter
    }

    companion object {
        const val MENU_CHOICES = "MenuChoice Names"
        const val RESTAURANT = "Restaurant Name"
    }
}