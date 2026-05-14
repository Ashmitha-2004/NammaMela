package com.example.nammamela

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("NammaMelaUser", MODE_PRIVATE)

        // Auto login only if user is logged in
        if (prefs.getBoolean("loggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val eyeIcon = findViewById<ImageView>(R.id.eyeIcon)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val signupText = findViewById<TextView>(R.id.signupText)
        val roleGroup = findViewById<RadioGroup>(R.id.roleGroup)

        var isPasswordVisible = false

        eyeIcon.setOnClickListener {
            if (isPasswordVisible) {
                password.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                password.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

            password.setSelection(password.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        loginBtn.setOnClickListener {

            val enteredUser = username.text.toString().trim()
            val enteredPass = password.text.toString().trim()
            val selectedId = roleGroup.checkedRadioButtonId

            if (enteredUser.isEmpty() || enteredPass.isEmpty()) {
                Toast.makeText(
                    this,
                    "Enter Username and Password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // MANAGER LOGIN
            if (selectedId == R.id.radioManager) {

                if (enteredUser == "manager" &&
                    enteredPass == "mela123"
                ) {

                    startActivity(
                        Intent(
                            this,
                            UpdatePlayActivity::class.java
                        )
                    )
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        "Invalid Manager Login",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {

                val savedUser =
                    prefs.getString("username", "")

                val savedPass =
                    prefs.getString("password", "")

                if (enteredUser == savedUser &&
                    enteredPass == savedPass
                ) {

                    prefs.edit()
                        .putBoolean("loggedIn", true)
                        .apply()

                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        "Invalid User Login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        signupText.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SignupActivity::class.java
                )
            )
        }
    }
}