package courses.projects.edible_eats

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

class UserPreferenceActivity : AppCompatActivity() {
    private var option1: CheckBox? = null
    private var option2: CheckBox? = null
    private var option3: CheckBox? = null
    private var option4: CheckBox? = null
    private var search: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preference)

        val dietSelection = findViewById<Spinner>(R.id.diet_dropdown)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        search = findViewById(R.id.search)

        search!!.setOnClickListener {
            search()
        }

        val dietOptions = resources.getStringArray(R.array.diets_array)

        dietSelection.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(applicationContext, "You selected " + dietOptions[position],
                    Toast.LENGTH_SHORT).show()
                when(dietOptions[position]){
                    "Pescatarian" ->{ displayPescatarianFoods() }
                    "Vegan" ->{ displayVeganFoods() }
                    "Vegetarian" ->{ displayVegetarianFoods() }
                    "Ketogenic" ->{ displayKetogenicFoods() }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code?
            }
        }



    }

    private fun displayPescatarianFoods() {
        clearSelections()
        option1!!.text = "Fish"
        option2!!.text = "Salad"
        option3!!.text = "Tacos"
        option4!!.text = "Fries"
    }

    private fun displayVeganFoods() {
        clearSelections()
        option1!!.text = "Vegetables"
        option2!!.text = "Salad"
        option3!!.text = "Tofu"
        option4!!.text = "Fruit"
    }

    private fun displayVegetarianFoods() {
        clearSelections()
        option1!!.text = "Fish"
        option2!!.text = "Salad"
        option3!!.text = "Fruit"
        option4!!.text = "Fries"
    }

    private fun displayKetogenicFoods() {
        clearSelections()
        option1!!.text = "Chicken"
        option2!!.text = "Turkey"
        option3!!.text = "Tacos"
        option4!!.text = "Beef"
    }

    private fun clearSelections() {
        option1!!.isChecked = false
        option2!!.isChecked = false
        option3!!.isChecked = false
        option4!!.isChecked = false
    }

    private fun search(){
        var intent = Intent(this@UserPreferenceActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)

        when (item.itemId) {
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
}