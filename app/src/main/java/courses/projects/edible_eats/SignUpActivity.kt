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

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var signUpButton: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var validator = SignUpVerification()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        signUpButton = findViewById(R.id.sign_up)
        progressBar = findViewById(R.id.progressBar)

        signUpButton!!.setOnClickListener {
            signUpNewUser()
        }
    }

    //
    private fun signUpNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()

        // Validates Email & Password
        if (!validator.verifyEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (!validator.verifyPassword(password)) {
            Toast.makeText(
                applicationContext,
                "Please enter a valid password (at least 6 characters with 1 letter and 1 " +
                        "number", Toast.LENGTH_LONG
            ).show()
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
}
