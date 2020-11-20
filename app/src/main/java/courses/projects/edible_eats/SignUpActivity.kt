package courses.projects.edible_eats

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var signUpButton: Button? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        signUpButton = findViewById(R.id.sign_up)

        signUpButton!!.setOnClickListener {
            signUpNewUser()
        }
    }

    private fun signUpNewUser() {

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()

        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Sign up successful!", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Sign up failed! Please try again later",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
