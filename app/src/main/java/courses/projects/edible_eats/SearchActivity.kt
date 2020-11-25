package courses.projects.edible_eats

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private var listView: ListView? = null
    private var adapter: ArrayAdapter<String>? = null
    private var restaurantList: ArrayList<String>? = null

    private val items = mutableListOf<courses.projects.edible_eats.MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        listView = findViewById(R.id.listView)
        restaurantList = ArrayList()
        populateRestaurantList()
        getFoodPreferences()

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantList!!)
        listView!!.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchViewItem: MenuItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as android.widget.SearchView
        searchView.queryHint = "Search Restaurants";
        searchView.setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (restaurantList!!.contains(query)) {
                        adapter!!.filter.filter(query)
                    } else {
                        Toast.makeText(this@SearchActivity,"Restaurant Not Found",
                            Toast.LENGTH_LONG).show()
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    adapter!!.filter.filter(newText)
                    return false
                }
            })

        return super.onCreateOptionsMenu(menu)
    }


     private fun getItem(): courses.projects.edible_eats.MenuItem =
         items.removeAt(items.size - 1)
     private fun addItem(item: courses.projects.edible_eats.MenuItem) {
         items.add(item)
     }




    private fun populateRestaurantList() {
        restaurantList!!.add("Chipotle")
        restaurantList!!.add("SweetGreen")
        restaurantList!!.add("Playa Bowls")
        restaurantList!!.add("NuVegan Cafe")
        restaurantList!!.add("Milk & Honey Cafe")
        restaurantList!!.add("Azteca Restaurant & Cantina")
        restaurantList!!.add("Marathon Deli")
        restaurantList!!.add("Saladworks")
        restaurantList!!.add("Panda Express")
        restaurantList!!.add("Bagel Place")
        restaurantList!!.add("Busboys & Poets")
        restaurantList!!.add("Mamma Lucia")

        restaurantList!!.sort()
    }

    private fun getFoodPreferences() {
        val diet = intent.getStringExtra(DIET_SELECTION)
        val preferences = intent.getStringArrayListExtra(FOOD_PREFERENCES)

        Log.d("DIET", diet!!)
        for(pref in preferences!!) {
            Log.d("PREFERENCE", pref)
        }

        Log.d("COUNT", preferences.size.toString())
    }

    companion object {
        const val FOOD_PREFERENCES = "Favorite Food"
        const val DIET_SELECTION = "Diet Preference"
    }

}