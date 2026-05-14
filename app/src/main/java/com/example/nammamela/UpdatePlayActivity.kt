package com.example.nammamela

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
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

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "namma_mela_db"   // ✅ SAME DB
        ).build()

        saveBtn.setOnClickListener {

            val play = Play(
                id = 1, // ✅ overwrite existing
                title = titleInput.text.toString(),
                time = timeInput.text.toString(),
                duration = durationInput.text.toString(),
                imageUrl = posterInput.text.toString() // ✅ FIXED NAME
            )

            lifecycleScope.launch {
                db.playDao().insert(play)

                runOnUiThread {
                    Toast.makeText(this@UpdatePlayActivity, "Updated!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
