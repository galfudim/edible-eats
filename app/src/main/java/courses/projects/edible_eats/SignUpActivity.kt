package courses.projects.edible_eats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var signUpButton: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        signUpButton = findViewById(R.id.sign_up)
        progressBar = findViewById(R.id.progressBar)

        signUpButton!!.setOnClickListener {
            signUpNewUser()
        }
    }

    // Some of the logic for signing up a user was modeled on the logic in Lab 7
    private fun signUpNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val email: String = mEmail!!.text.toString()
        val password: String = mPassword!!.text.toString()

        // Verifies that email & password are valid
        if (!verifiedEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email", Toast.LENGTH_LONG)
                .show()
            progressBar?.visibility = View.INVISIBLE
            return
        }

        if (!verifiedPassword(password)) {
            Toast.makeText(
                applicationContext,
                "Please enter a valid password (at least 6 characters with 1 letter and 1 " +
                        "number", Toast.LENGTH_LONG
            ).show()
            progressBar?.visibility = View.INVISIBLE
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Sign up successful!", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Sign up failed! Please try again later.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun verifiedEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            false
        } else {
            EMAIL_REGEX!!.matches(email)
        }
    }

    private fun verifiedPassword(password: String?): Boolean {
        return if (password.isNullOrEmpty()) {
            false
        } else {
            PASSWORD_REGEX!!.matches(password)
        }
    }

    companion object {
        var EMAIL_REGEX: Regex? = Regex(
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
                    "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x" +
                    "5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z" +
                    "0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4" +
                    "][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z" +
                    "0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                    "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"
        )
        var PASSWORD_REGEX: Regex? = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$")
    }
}
