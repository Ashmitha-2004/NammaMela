package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView   // ✅ ADD THIS
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        // 🔙 Back button
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            // 🔥 Clears all previous screens
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }


        // 🎟 Ticket data
        val playText = findViewById<TextView>(R.id.ticketPlay)
        val seatText = findViewById<TextView>(R.id.ticketSeat)
        val timeText = findViewById<TextView>(R.id.ticketTime)

        val play = intent.getStringExtra("play") ?: "Namma Mela"
        val seat = intent.getStringExtra("seat") ?: "N/A"
        val time = intent.getStringExtra("time") ?: "7:00 PM"

        playText.text = "Play: $play"
        seatText.text = "Seat: $seat"
        timeText.text = "Time: $time"
    }
}
