package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val play = intent.getStringExtra("play")
        val seat = intent.getStringExtra("seat")
        val time = intent.getStringExtra("time")

        // Block fake opening
        if (play.isNullOrEmpty() ||
            seat.isNullOrEmpty() ||
            time.isNullOrEmpty()
        ) {
            Toast.makeText(
                this,
                "Invalid Ticket",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
            return
        }

        setContentView(R.layout.activity_ticket)

        findViewById<ImageView>(R.id.backBtn)
            .setOnClickListener {

                val intent =
                    Intent(this, MainActivity::class.java)

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }

        findViewById<TextView>(R.id.ticketPlay).text =
            "Play: $play"

        findViewById<TextView>(R.id.ticketSeat).text =
            "Seat: $seat"

        findViewById<TextView>(R.id.ticketTime).text =
            "Time: $time"
    }
}