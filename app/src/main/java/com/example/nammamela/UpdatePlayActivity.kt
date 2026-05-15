package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UpdatePlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_play)

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val timeInput = findViewById<EditText>(R.id.timeInput)
        val durationInput = findViewById<EditText>(R.id.durationInput)
        val posterInput = findViewById<EditText>(R.id.posterInput)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val updateCastBtn = findViewById<Button>(R.id.updateCastBtn)

        val previewTitle = findViewById<TextView>(R.id.tvPreviewTitle)
        val previewTime = findViewById<TextView>(R.id.tvPreviewTime)
        val previewDuration = findViewById<TextView>(R.id.tvPreviewDuration)

        val db = AppDatabase.getDatabase(this)

        logoutBtn.setOnClickListener {
            SessionManager.setRole(this, SessionManager.ROLE_USER)

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        saveBtn.setOnClickListener {

            val title = titleInput.text.toString().trim()
            val time = timeInput.text.toString().trim()
            val duration = durationInput.text.toString().trim()
            val imageUrl = posterInput.text.toString().trim()

            if (title.isEmpty() || time.isEmpty() || duration.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
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

                val showKey = "${title}_${time}"
                db.seatDao().clearShowSeats(showKey)

                val seats = mutableListOf<Seat>()
                val rows = listOf("A", "B", "C", "D", "E")

                for (r in rows) {
                    for (i in 1..5) {
                        seats.add(
                            Seat(
                                seatId = "$r$i",
                                showKey = showKey,
                                isBooked = false,
                                bookedByUser = false
                            )
                        )
                    }
                }

                db.seatDao().insertAll(seats)

                runOnUiThread {
                    Toast.makeText(
                        this@UpdatePlayActivity,
                        "Play Updated Successfully ✅",
                        Toast.LENGTH_SHORT
                    ).show()

                    previewTitle.text = "Title: $title"
                    previewTime.text = "Time: $time"
                    previewDuration.text = "Duration: $duration"
                }
            }
        }

        updateCastBtn.setOnClickListener {
            startActivity(Intent(this, UpdateCastActivity::class.java))
        }
    }
}