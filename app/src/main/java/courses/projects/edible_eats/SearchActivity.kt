package courses.projects.edible_eats

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class SearchActivity : AppCompatActivity() {
    // Firebase database objects
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReferenceMenuChoices: DatabaseReference? = null

    // Menu-related objects
    private var menuChoices: ArrayList<MenuChoice>? = ArrayList<MenuChoice>()
    private var restaurantToMenuChoices: HashMap<String, ArrayList<MenuChoice>>? = HashMap<String, ArrayList<MenuChoice>>()

    // View objects
    private var listView: ListView? = null
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mDatabase = FirebaseDatabase.getInstance()
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        //  Progress Dialog to simulate retrieving data
//        var mProgress = ProgressDialog.show(
//            this, "Loading Eateries!",
//            "Restaurants are loading. Please wait.", false
//        )

        //val handler = Handler()

        // Loading data from database
        if (menuChoices!!.isEmpty()) {


            readMenuChoiceData(object : MenuChoiceCallback {
                override fun onCallbackMenuChoice(value: MenuChoice) {
                    Log.i("Search activity", "menu choice is empty")
                    // For every menuChoice in DB add to list
                    // Once all choices loaded, filter based on restaurant & choices
                    menuChoices!!.add(value)
                    restaurantToMenuChoices!!.clear()
                    val filteredRestaurants = getRestaurantToMenuChoices()
                    adapter!!.addAll(filteredRestaurants!!.keys.toList().distinct())


                    // TODO (Aimon) onClick go to ListActivity via intent to populate menuChoices,
                    //  iterate though values to get menuChoices for each restaurant
                    listView!!.setOnItemClickListener { adapterView, view, i, l ->
                        val intent = Intent(this@SearchActivity, ListActivity::class.java)
                        intent.putExtra("RestaurantName", listView!!.getItemAtPosition(i).toString())
                        intent.putExtra("MenuChoices", filteredRestaurants.get( listView!!.getItemAtPosition(i).toString()))

                       startActivity(intent)
                    }
                    //handler.postDelayed(Runnable { mProgress.dismiss() }, 2000)
                    listView!!.adapter = adapter
                }

            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchViewItem: MenuItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as android.widget.SearchView
        searchView.queryHint = "Search Restaurants"
        searchView.setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (restaurantToMenuChoices!!.contains(query)) {
                        adapter!!.filter.filter(query)
                    } else {
                        Toast.makeText(
                            this@SearchActivity, "Restaurant Not Found",
                            Toast.LENGTH_LONG
                        ).show()
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

    private fun getRestaurantToMenuChoices(): HashMap<String, ArrayList<MenuChoice>>? {
        val diet = intent.getStringExtra(DIET_SELECTION) as String
        val preferences = intent.getStringArrayListExtra(FOOD_PREFERENCES) as ArrayList<String>

        if (menuChoices!!.size == ALL_CHOICES_LOADED) {
            for (menuChoice in menuChoices!!) {
                if (menuChoice.diet == diet && (menuChoice.preferences!!.intersect(preferences)).isNotEmpty()) {
                    var list: ArrayList<MenuChoice> = ArrayList()

                    if (restaurantToMenuChoices!![menuChoice.restaurant] == null ) {
                        restaurantToMenuChoices!![menuChoice.restaurant.toString()] = list
                    }
                    if (restaurantToMenuChoices!![menuChoice.restaurant]!!.isNotEmpty()) {
                        list = restaurantToMenuChoices!![menuChoice.restaurant]!!
                        list.add(menuChoice)
                        restaurantToMenuChoices!![menuChoice.restaurant!!] = list
                    } else {
                        list.add(menuChoice)
                        restaurantToMenuChoices!![menuChoice.restaurant!!] = list
                    }
                }
            }
        }

        return restaurantToMenuChoices
    }

    private fun readMenuChoiceData(callback: MenuChoiceCallback) {
        mDatabaseReferenceMenuChoices = mDatabase!!.reference.child("menuChoices")
        mDatabaseReferenceMenuChoices!!.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ALL_CHOICES_LOADED = dataSnapshot.children.count()

                for (menuChoiceSnapshot in dataSnapshot.children) {

                    val menuChoice = menuChoiceSnapshot.getValue(MenuChoice::class.java)
                    Log.i("Search activity", menuChoice!!.name.toString())
                    callback.onCallbackMenuChoice(menuChoice!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Do nothing
            }
        })
    }

    companion object {
        const val FOOD_PREFERENCES = "Favorite Foods"
        const val DIET_SELECTION = "Diet Preference"
        var ALL_CHOICES_LOADED = -1
    }
}