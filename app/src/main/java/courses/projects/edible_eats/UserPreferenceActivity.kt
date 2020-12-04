package courses.projects.edible_eats

import android.app.AlertDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import kotlin.collections.ArrayList


class UserPreferenceActivity : AppCompatActivity() {
    private var option1: CheckBox? = null
    private var option2: CheckBox? = null
    private var option3: CheckBox? = null
    private var option4: CheckBox? = null
    private var search: Button? = null
    private var dietOption: String? = null
    private var profileName: TextView? = null
    private var foodOptions: LinearLayout? = null
    private var select: TextView? = null
    private var imagesArray: Array<String>? = null
    private var currentPage = 0
    private var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preference)

        val dietSelection = findViewById<Spinner>(R.id.diet_dropdown)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        search = findViewById(R.id.search)
        profileName = findViewById(R.id.profile_name_text)
        foodOptions = findViewById(R.id.food_options)
        select = findViewById(R.id.select_text)
        viewPager = findViewById(R.id.viewPager)

        // Sliding images view
        updateViewPager()

        // Navigate to Search Activity
        search!!.setOnClickListener {
            search()
        }

        // Utilized for diet selection
        val dietOptions = resources.getStringArray(R.array.diets_array)

        // Default Spinner selection
        dietSelection.setSelection(0, false)
        addDietSelection(SELECT)
        select!!.visibility = INVISIBLE
        foodOptions!!.visibility = INVISIBLE

        dietSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                select!!.visibility = VISIBLE
                foodOptions!!.visibility = VISIBLE
                var toast = Toast.makeText(
                    applicationContext, "You selected " + dietOptions[position], Toast.LENGTH_SHORT
                )

                when (dietOptions[position]) {
                    SELECT -> {
                        select!!.visibility = INVISIBLE
                        foodOptions!!.visibility = INVISIBLE
                        clearSelections()
                    }
                    "Pescatarian" -> {
                        displayFoods("Fish", "Salad", "Taco", "Fries")
                        option4!!.visibility = VISIBLE
                        toast.show()
                    }
                    "Vegan" -> {
                        displayFoods("Vegetables", "Salad", "Tofu", "Fruit")
                        option4!!.visibility = VISIBLE
                        toast.show()
                    }
                    "Vegetarian" -> {
                        displayFoods("Faux meat", "Salad", "Taco", "Fries")
                        option4!!.visibility = VISIBLE
                        toast.show()
                    }
                    "Ketogenic" -> {
                        displayFoods("Chicken", "Turkey", "Beef","")
                        option4!!.visibility = INVISIBLE
                        toast.show()
                    }
                }

                addDietSelection(dietOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

    }

    private fun displayFoods(food1: String, food2: String, food3: String, food4: String) {
        clearSelections()
        option1!!.text = food1
        option2!!.text = food2
        option3!!.text = food3
        option4!!.text = food4
    }

    private fun clearSelections() {
        option1!!.isChecked = false
        option2!!.isChecked = false
        option3!!.isChecked = false
        option4!!.isChecked = false
    }

    private fun addFoodPreferences(): ArrayList<String> {
        var foodPreferences = ArrayList<String>()

        if (option1!!.isChecked) {
            foodPreferences.add(option1!!.text as String)
        }

        if (option2!!.isChecked) {
            foodPreferences.add(option2!!.text as String)
        }

        if (option3!!.isChecked) {
            foodPreferences.add(option3!!.text as String)
        }

        if (option4!!.isChecked) {
            foodPreferences.add(option4!!.text as String)
        }

        return foodPreferences
    }

    private fun addDietSelection(option: String) {
        dietOption = option
    }

    private fun search() {
        var intent = Intent(this@UserPreferenceActivity, SearchActivity::class.java)
        // Validate selection
        if (dietOption == SELECT) {
            Toast.makeText(
                applicationContext, "Please choose a diet option!", Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!option1!!.isChecked && !option2!!.isChecked && !option3!!.isChecked && !option4!!.isChecked) {
            Toast.makeText(
                applicationContext, "Please select a food preference!", Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Send information for what user selected
        intent.putExtra(DIET_SELECTION, dietOption)
        intent.putExtra(FOOD_PREFERENCES, addFoodPreferences())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dlg: AlertDialog.Builder = AlertDialog.Builder(this)

        when (item.itemId) {
            R.id.profile_name -> {
                profileName!!.text = getString(R.string.pick_your_preferences)
                val profile = EditText(this)

                dlg.setMessage("Enter profile name")
                dlg.setTitle("Update Profile Name")
                dlg.setView(profile)
                dlg.setIcon(R.drawable.profile_icon)

                dlg.setPositiveButton("Done") { _, _ ->
                    val name = profile.text.toString()
                    profileName!!.text = profileName!!.text.toString() + ", " + name + "!"
                }

                dlg.setNegativeButton("Cancel") { _, _ ->
                    dlg.create().dismiss()
                }

                dlg.show()
                return true
            }
            R.id.Vegan -> {
                dlg.setMessage(R.string.vegan_info)
                dlg.create().show()
                return true
            }
            R.id.Vegetarian -> {
                dlg.setMessage(R.string.vegetarian_info)
                dlg.create().show()
                return true
            }
            R.id.Pescatarian -> {
                dlg.setMessage(R.string.pescatarian_info)
                dlg.create().show()
                return true
            }
            R.id.Ketogenic -> {
                dlg.setMessage(R.string.ketogenic_info)
                dlg.create().show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateViewPager() {
        imagesArray = resources.getStringArray(R.array.images_array)

        val landingImagesAdapter = CustomImageSlidingAdapter(this, imagesArray!!.size)
        viewPager?.apply {
            adapter = landingImagesAdapter
        }

        val handler = Handler()
        val update = Runnable {
            if (currentPage == imagesArray!!.size) {
                currentPage = 0
            }

            // Scroll to next item
            viewPager!!.setCurrentItem(currentPage++, true)
        }

        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3500, 3500)
    }


    companion object {
        const val FOOD_PREFERENCES = "Favorite Foods"
        const val DIET_SELECTION = "Diet Preference"
        const val SELECT = "Select a diet..."
    }
}