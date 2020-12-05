package courses.projects.edible_eats


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuChoiceActivity : AppCompatActivity() {
    private var mlistView: ListView? = null
    private lateinit var restaurantName: TextView
    private var menuChoicesList: ArrayList<String>? = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_choice)

        mlistView = findViewById<View>(R.id.listView) as ListView

        val restaurant = intent.getStringExtra(RESTAURANT)

        restaurantName = findViewById(R.id.restName)
        restaurantName.text = restaurant

        menuChoicesList = intent.getStringArrayListExtra(MENU_CHOICES)
        val mAdapter = ArrayAdapter<String>(
            this@MenuChoiceActivity,
            R.layout.custom_centered_item,
            menuChoicesList!!
        )

        mlistView!!.adapter = mAdapter
    }

    companion object {
        const val MENU_CHOICES = "MenuChoice Names"
        const val RESTAURANT = "Restaurant Name"
    }
}