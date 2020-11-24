package courses.projects.edible_eats

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class UserPreferenceActivity : AppCompatActivity() {
    private var option1: CheckBox? = null
    private var option2: CheckBox? = null
    private var option3: CheckBox? = null
    private var option4: CheckBox? = null
    private var search: Button? = null
    private var dietOption: String? = null
    private var profileName: TextView? = null

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

        // Navigate to Search Activity
        search!!.setOnClickListener {
            search()
        }

        // Utilized for diet selection
        val dietOptions = resources.getStringArray(R.array.diets_array)

        dietSelection.setSelection(0,false)
        dietSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(dietOptions[position]) {
                    "Pescatarian" -> {
                        displayFoods("Fish", "Salad", "Taco", "Fries")
                    }
                    "Vegan" -> {
                        displayFoods("Vegetables", "Salad", "Tofu", "Fruit")
                    }
                    "Vegetarian" -> {
                        displayFoods("Faux meat", "Salad", "Taco", "Fries")
                    }
                    "Ketogenic" -> {
                        displayFoods("Chicken", "Turkey", "Taco", "Beef")
                    }
                }

                Toast.makeText(
                    applicationContext, "You selected " + dietOptions[position], Toast.LENGTH_SHORT
                ).show()

                addDietSelection(dietOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

    }

    private fun displayFoods(food1:String, food2:String, food3:String, food4:String) {
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

        if(option1!!.isChecked){
            foodPreferences.add(option1!!.text as String)
        }

        if(option2!!.isChecked){
            foodPreferences.add(option2!!.text as String)
        }

        if(option3!!.isChecked){
            foodPreferences.add(option3!!.text as String)
        }

        if(option4!!.isChecked){
            foodPreferences.add(option4!!.text as String)
        }

        return foodPreferences
    }

    private fun addDietSelection(option: String) {
        dietOption = option
    }

    private fun search(){
        var intent = Intent(this@UserPreferenceActivity, SearchActivity::class.java)
        // TODO: put information for what user selected
        if(!option1!!.isChecked && !option2!!.isChecked && !option3!!.isChecked && !option4!!.isChecked){
            Toast.makeText(
                applicationContext, "Please make a selection!", Toast.LENGTH_SHORT
            ).show()
            return
        }
        intent.putExtra(DIET_SELECTION, dietOption)
        intent.putExtra(FOOD_PREFERENCES, addFoodPreferences())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)

        when (item.itemId) {
            R.id.profile_name -> {
                profileName!!.text = getString(R.string.pick_your_preferences)
                val profile = EditText(this)

                dlg.setMessage("Enter profile name")
                dlg.setTitle("Update Profile Name")
                dlg.setView(profile)

                dlg.setPositiveButton("Done") { _, _ ->
                    val name = profile.text.toString()
                    profileName!!.text = profileName!!.text.toString() + ", " + name
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

    companion object {
        const val FOOD_PREFERENCES = "Favorite Food"
        const val DIET_SELECTION = "Diet Preference"
    }
}