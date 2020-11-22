package courses.projects.edible_eats

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast

class UserPreferenceActivity : AppCompatActivity() {
    private var option1: CheckBox? = null
    private var option2: CheckBox? = null
    private var option3: CheckBox? = null
    private var option4: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preference)

        val dietSelection = findViewById<Spinner>(R.id.diet_dropdown)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

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
    }

    private fun displayVegetarianFoods() {
        clearSelections()
    }

    private fun displayKetogenicFoods() {
        clearSelections()
    }

    private fun clearSelections() {
        option1!!.isChecked = false
        option2!!.isChecked = false
        option3!!.isChecked = false
        option4!!.isChecked = false
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