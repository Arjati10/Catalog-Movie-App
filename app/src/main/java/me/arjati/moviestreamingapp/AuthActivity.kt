package me.arjati.moviestreamingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = Firebase.auth

        val btnSigIn = findViewById<Button>(R.id.btnSignIn)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)

        btnSigIn.setOnClickListener{
            signInFireBase(etEmail.text.toString(), etPassword.text.toString())
        }

        btnSignUp.setOnClickListener{
            signUpFireBase(etEmail.text.toString(), etPassword.text.toString())
        }
    }


    private fun signUpFireBase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed : ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInFireBase(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    startActivity(
                        Intent(this, MainActivity::class.java)
                            .putExtra("user", user!!.email)
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun storeEmailToPreference(email: String) {
        val sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        sharedPref.edit().putString("email", email).apply()
    }

    override fun onStart() {
        super.onStart()
        var currentUser = auth.getCurrentUser()

        if(currentUser != null){
            startActivity(
                Intent(this, MainActivity::class.java).putExtra(
                    "user",
                    currentUser.email
                )
            )
            finish()
        }
    }
}