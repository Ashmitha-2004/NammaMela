package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val roleGroup = findViewById<RadioGroup>(R.id.roleGroup)
        val signupText = findViewById<TextView>(R.id.signupText)

        loginBtn.setOnClickListener {

            val selectedId = roleGroup.checkedRadioButtonId

            if (selectedId == R.id.radioUser) {
                // 👉 USER FLOW
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // 👉 MANAGER FLOW
                startActivity(Intent(this, UpdatePlayActivity::class.java))
            }
        }

        signupText.setOnClickListener {
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
