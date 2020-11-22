package courses.projects.edible_eats

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView


class SearchActivity : AppCompatActivity() {
    private var listView: ListView? = null
    private var adapter: ArrayAdapter<String>? = null
    private var restaurantList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        listView = findViewById(R.id.listView)
        restaurantList = ArrayList()
        populateRestaurantList()

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantList!!)
        listView!!.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchViewItem: MenuItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as android.widget.SearchView
        searchView.setQueryHint("Search Restaurants");
        searchView.setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (restaurantList!!.contains(query)) {
                        adapter!!.filter.filter(query)
                    } else {
                        Toast.makeText(this@SearchActivity,"Not found",
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

    private fun populateRestaurantList(){
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


}