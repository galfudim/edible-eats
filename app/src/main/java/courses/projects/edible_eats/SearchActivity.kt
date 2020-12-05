package courses.projects.edible_eats

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


@Suppress("DEPRECATION")
class SearchActivity : AppCompatActivity() {
    // Firebase database objects
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReferenceMenuChoices: DatabaseReference? = null

    // Menu-related objects
    private var menuChoices: ArrayList<MenuChoice>? = ArrayList()
    private var formattedEateries: ArrayList<String>? = ArrayList()
    private var restaurantToMenuChoices: HashMap<String, ArrayList<MenuChoice>>? = HashMap()
    private var filtered: ArrayList<String>? = ArrayList()

    // View objects
    private var mListView: ListView? = null
    private var mAdapter: ArrayAdapter<String>? = null
    private var mDisplayAdapter: ListItemAdapter? = null
    private var mProgress: ProgressDialog? = null
    private var mHandler: Handler? = null

    var restaurantToLocation: HashMap<String, String>? = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mDatabase = FirebaseDatabase.getInstance()
        mListView = findViewById(R.id.listView)
        mAdapter = ArrayAdapter<String>(this, R.layout.custom_centered_item)

        //  Progress Dialog to simulate retrieving data
        mProgress = ProgressDialog.show(
            this, "Loading Eateries!",
            "Restaurants are loading. Please wait.", false
        )

        mHandler = Handler()

        restaurantToLocation = hashMapOf(
            "Chipotle" to COLLEGE_PARK,
            "Panda Express" to COLLEGE_PARK,
            "Bagel Place" to COLLEGE_PARK,
            "Busboys & Poets" to HYATTSVILLE,
            "SweetGreen" to COLLEGE_PARK,
            "Playa Bowls" to COLLEGE_PARK,
            "NuVegan Cafe" to COLLEGE_PARK,
            "Milk & Honey Cafe" to COLLEGE_PARK,
            "Azteca Restaurant & Cantina" to COLLEGE_PARK,
            "Marathon Deli" to COLLEGE_PARK,
            "Mamma Lucia" to COLLEGE_PARK,
            "Saladworks" to COLLEGE_PARK
        )

        // Loading data from database
        if (menuChoices!!.isEmpty()) {
            readMenuChoiceData(object : MenuChoiceCallback {
                override fun onCallbackMenuChoice(value: MenuChoice) {
                    // For every menuChoice in DB add to list
                    // Once all choices loaded, filter based on restaurant & choices
                    menuChoices!!.add(value)
                    restaurantToMenuChoices!!.clear()
                    val filteredRestaurants = getRestaurantToMenuChoices()
                    for (restaurant in filteredRestaurants!!.keys) {
                        formattedEateries!!.add(restaurant + ": " + restaurantToLocation!![restaurant])
                    }

                    val sortedRestaurants = formattedEateries!!.sorted()
                    mDisplayAdapter = ListItemAdapter(applicationContext, R.layout.custom_list_item,
                        sortedRestaurants
                    )
                    mAdapter!!.addAll(sortedRestaurants)

                    // Fetch data progress and populate list
                    mHandler!!.postDelayed(Runnable { mProgress!!.dismiss() }, 1000)
                    mListView!!.adapter = mDisplayAdapter

                    //  onClick go to ListActivity via intent to populate menuChoices,
                    //  iterate though values to get menuChoices for each restaurant
                    mListView!!.setOnItemClickListener { adapterView, view, i, l ->
                        view.isSelected = true
                        val intent = Intent(this@SearchActivity, MenuChoiceActivity::class.java)
                        val restaurantName = mListView!!.getItemAtPosition(i).toString().split(":")[0]
                        intent.putExtra(RESTAURANT, restaurantName)
                        filtered!!.clear()
                        for (choice in filteredRestaurants[restaurantName]!!) {
                            choice.name?.let { filtered!!.add(it) }
                        }
                        intent.putExtra(MENU_CHOICES, filtered)
                        startActivity(intent)
                    }
                }

            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchViewItem: MenuItem = menu.findItem(R.id.search_bar)
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = "Search Restaurants"
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (restaurantToMenuChoices!!.contains(query)) {
                        mAdapter!!.filter.filter(query)
//                        mDisplayAdapter!!.filter.filter(query)
                    } else {
                        Toast.makeText(
                            this@SearchActivity, "Restaurant Not Found",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    mAdapter!!.filter.filter(newText)
//                  mDisplayAdapter!!.filter.filter(newText)
//                  val items = mDisplayAdapter!!.getDataChanged()

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

                    if (restaurantToMenuChoices!![menuChoice.restaurant] == null) {
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
        mDatabaseReferenceMenuChoices!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ALL_CHOICES_LOADED = dataSnapshot.children.count()

                for (menuChoiceSnapshot in dataSnapshot.children) {

                    val menuChoice = menuChoiceSnapshot.getValue(MenuChoice::class.java)
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
        const val MENU_CHOICES = "MenuChoice Names"
        const val RESTAURANT = "Restaurant Name"

        const val COLLEGE_PARK = "College Park, MD"
        const val HYATTSVILLE = "Hyattsville, MD"
        var ALL_CHOICES_LOADED = -1
    }
}