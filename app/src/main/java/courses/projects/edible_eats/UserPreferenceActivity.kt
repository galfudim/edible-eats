package courses.projects.edible_eats

import android.app.AlertDialog
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

class UserPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preference)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }
    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        if (item.itemId == R.id.Vegan) {
            val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)
            dlg
                    .setMessage(R.string.vegan_info)
            dlg.create().show()
            return true
        } else if(item.itemId == R.id.Vegetarian) {
            val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)
            dlg
                    .setMessage(R.string.vegetarian_info)
            dlg.create().show()
            return true
        }else if (item.itemId == R.id.Pescatarian) {
            val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)
            dlg
                    .setMessage(R.string.pescatarian_info)
            dlg.create().show()
            return true
        } else if (item.itemId == R.id.Ketogenic) {
            val dlg: AlertDialog.Builder =  AlertDialog.Builder(this)
            dlg
                    .setMessage(R.string.ketogenic_info)
            dlg.create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}