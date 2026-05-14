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

class UpdatePlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_play)

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val timeInput = findViewById<EditText>(R.id.timeInput)
        val durationInput = findViewById<EditText>(R.id.durationInput)
        val posterInput = findViewById<EditText>(R.id.posterInput)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val updateCastBtn = findViewById<Button>(R.id.updateCastBtn)
        val confirmBtn = findViewById<Button>(R.id.confirmBtn)

        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {

            val title = titleInput.text.toString().trim()
            val time = timeInput.text.toString().trim()
            val duration = durationInput.text.toString().trim()
            val imageUrl = posterInput.text.toString().trim()

            if (
                title.isEmpty() ||
                time.isEmpty() ||
                duration.isEmpty() ||
                imageUrl.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Toast.makeText(
                    this,
                    "Invalid image URL",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.playDao().deleteAll()

                db.playDao().insert(
                    Play(
                        id = 1,
                        title = title,
                        time = time,
                        duration = duration,
                        imageUrl = imageUrl
                    )
                )

                runOnUiThread {
                    Toast.makeText(
                        this@UpdatePlayActivity,
                        "Play Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        updateCastBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    UpdateCastActivity::class.java
                )
            )
        }

        confirmBtn.setOnClickListener {

            lifecycleScope.launch {

                val play = db.playDao().getPlay()

                runOnUiThread {

                    if (play == null) {
                        Toast.makeText(
                            this@UpdatePlayActivity,
                            "Update play first",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        startActivity(
                            Intent(
                                this@UpdatePlayActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}