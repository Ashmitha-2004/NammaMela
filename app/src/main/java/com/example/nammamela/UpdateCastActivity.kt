package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UpdateCastActivity : AppCompatActivity() {

    private lateinit var nameEdit: EditText
    private lateinit var roleEdit: EditText
    private lateinit var imageEdit: EditText
    private lateinit var updateBtn: Button
    private lateinit var castDao: CastDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_cast)

        nameEdit = findViewById(R.id.castNameEdit)
        roleEdit = findViewById(R.id.castRoleEdit)
        imageEdit = findViewById(R.id.castImageEdit)
        updateBtn = findViewById(R.id.updateCastButton)

        castDao = AppDatabase.getDatabase(this).castDao()

        updateBtn.setOnClickListener {
            updateCast()
        }
    }

    private fun updateCast() {

        if (
            nameEdit.text.toString().trim().isEmpty() ||
            roleEdit.text.toString().trim().isEmpty() ||
            imageEdit.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(
                this,
                "All fields are required",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val names = nameEdit.text.toString().split("|")
        val roles = roleEdit.text.toString().split("|")
        val images = imageEdit.text.toString().split("|")

        if (
            names.size != roles.size ||
            roles.size != images.size
        ) {
            Toast.makeText(
                this,
                "Enter equal values separated by |",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        for (url in images) {
            if (!Patterns.WEB_URL.matcher(url.trim()).matches()) {
                Toast.makeText(
                    this,
                    "Invalid image URL",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }

        lifecycleScope.launch {

            castDao.deleteAll()

            for (i in names.indices) {
                castDao.insert(
                    Cast(
                        name = names[i].trim(),
                        role = roles[i].trim(),
                        imageUrl = images[i].trim()
                    )
                )
            }

            runOnUiThread {

                Toast.makeText(
                    this@UpdateCastActivity,
                    "Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this@UpdateCastActivity,
                        UpdatePlayActivity::class.java
                    )
                )

                finish()
            }
        }
    }
}