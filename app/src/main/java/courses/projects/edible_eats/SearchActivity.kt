package courses.projects.edible_eats

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class SearchActivity : AppCompatActivity() {
    // Firebase database objects
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReferenceMenuChoices: DatabaseReference? = null
    private var mDatabaseReferenceRestaurants: DatabaseReference? = null

    // Menu-related objects
    private var menuChoices: ArrayList<MenuChoice>? = ArrayList<MenuChoice>()
    private var restaurants: ArrayList<String>? = ArrayList<String>()
    private var restaurantToMenuChoices: HashMap<String, ArrayList<MenuChoice>>? = null

    // View objects
    private var listView: ListView? = null
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mDatabase = FirebaseDatabase.getInstance()

        // TODO: Modifying Database initially?
//        mDatabase!!.reference.child("Diet").setValue(intent.getStringExtra(DIET_SELECTION))
//        mDatabase!!.reference.child("Preferences").setValue(intent.getStringArrayListExtra(FOOD_PREFERENCES)!!)
//        mDatabase!!.reference.child("menuChoices").child("menuChoice1").child("User Choice").setValue("CHOSEN")

        menuChoices = getMenuChoices()
        restaurants = getRestaurants()
        restaurantToMenuChoices = getRestaurantToMenuChoices()

        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantToMenuChoices!!.keys.toList())
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
                    if (restaurants!!.contains(query)) {
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

    private fun getMenuChoices(): ArrayList<MenuChoice> {
        val menuChoiceList = ArrayList<MenuChoice>()

        mDatabaseReferenceMenuChoices = mDatabase!!.reference.child("menuChoices")
        mDatabaseReferenceMenuChoices!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (menuChoiceSnapshot in snapshot.children) {
                    val menuChoice = menuChoiceSnapshot.getValue(MenuChoice::class.java)
                    menuChoiceList.add(menuChoice!!)
                }
                Log.d("MENU CHOICES WORKS", menuChoices!!.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
        return menuChoiceList
    }

    private fun getRestaurants(): ArrayList<String> {
        val restaurantList = ArrayList<String>()

        mDatabaseReferenceRestaurants = mDatabase!!.reference.child("restaurants")
        mDatabaseReferenceRestaurants!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (restaurantSnapshot in snapshot.children) {
                    val restaurant = restaurantSnapshot.value.toString()
                    restaurantList.add(restaurant)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })

        restaurantList.sort()
        return restaurantList
    }

    private fun getRestaurantToMenuChoices(): HashMap<String, ArrayList<MenuChoice>> {
        Log.d("MENU CHOICES", menuChoices!!.size.toString())

        val diet = intent.getStringExtra(DIET_SELECTION) as String
        val preferences = intent.getStringArrayListExtra(FOOD_PREFERENCES) as ArrayList<String>
        val restaurantToMenuChoicesMap = HashMap<String, ArrayList<MenuChoice>>()

        for (menuChoice in menuChoices!!) {
            if (menuChoice.diet == diet && (menuChoice.preferences!!.intersect(preferences)).isNotEmpty()) {
                var list: ArrayList<MenuChoice> = ArrayList()
                if (restaurantToMenuChoicesMap!![menuChoice.restaurantName]!!.isNotEmpty()) {
                    list = restaurantToMenuChoicesMap!![menuChoice.restaurantName]!!
                    list.add(menuChoice)
                    restaurantToMenuChoicesMap!![menuChoice.restaurantName!!] = list
                } else {
                    list.add(menuChoice)
                    restaurantToMenuChoicesMap!![menuChoice.restaurantName!!] = list
                }
            }
        }

        return restaurantToMenuChoicesMap
    }

    companion object {
        const val FOOD_PREFERENCES = "Favorite Foods"
        const val DIET_SELECTION = "Diet Preference"
    }
}