package com.example.nammamela

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        prefs = getSharedPreferences("NammaMelaUser", MODE_PRIVATE)

        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        val username = findViewById<EditText>(R.id.signupUsername)
        val password = findViewById<EditText>(R.id.signupPassword)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val signupBtn = findViewById<Button>(R.id.signupBtn)

        backBtn.setOnClickListener {
            finish()
        }

        signupBtn.setOnClickListener {

            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()
            val confirm = confirmPassword.text.toString().trim()

            when {
                user.isEmpty() || pass.isEmpty() || confirm.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                pass != confirm -> {
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    prefs.edit()
                        .putString("username", user)
                        .putString("password", pass)
                        .apply()

                    Toast.makeText(
                        this,
                        "Signup Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
            }
        }
    }
}