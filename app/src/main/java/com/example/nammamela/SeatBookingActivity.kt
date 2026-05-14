package com.example.nammamela

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var seatGrid: GridLayout
    private lateinit var selectedSeatsText: TextView
    private lateinit var confirmBtn: Button
    private lateinit var generateTicketBtn: Button

    private val selectedSeats = mutableListOf<String>()
    private val bookedSeats = mutableSetOf<String>()

    private var isBookingConfirmed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            finish()
        }

        seatGrid = findViewById(R.id.seatGrid)
        selectedSeatsText = findViewById(R.id.tvSelectedSeats)
        confirmBtn = findViewById(R.id.btnConfirm)
        generateTicketBtn = findViewById(R.id.generateTicketBtn)

        generateTicketBtn.visibility = View.GONE

        loadBookedSeats()
        createSeats()

        // ✅ CONFIRM BOOKING (unchanged logic + small addition)
        confirmBtn.setOnClickListener {

            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "No seats selected", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            bookedSeats.addAll(selectedSeats)
            saveBookedSeats()

            Toast.makeText(this, "Booked: $selectedSeats", Toast.LENGTH_SHORT).show()

            isBookingConfirmed = true
            generateTicketBtn.visibility = View.VISIBLE

            selectedSeatsText.text = selectedSeats.joinToString(", ")

            // Refresh UI
            seatGrid.removeAllViews()
            createSeats()
        }

        // 🎟 GENERATE TICKET
        generateTicketBtn.setOnClickListener {

            if (!isBookingConfirmed) return@setOnClickListener

            val intent = Intent(this, TicketActivity::class.java)
            intent.putExtra("play", "Veera Kathai")
            intent.putExtra("seat", selectedSeats.joinToString(", "))
            intent.putExtra("time", "7:00 PM")
            startActivity(intent)
        }
    }

    // 🔹 Create seat grid
    private fun createSeats() {
        val rows = listOf("A", "B", "C", "D", "E")

        for (row in rows) {
            for (i in 1..5) {
                val seatId = "$row$i"

                val btn = Button(this)
                btn.text = seatId

                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = 150
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                params.setMargins(8, 8, 8, 8)
                btn.layoutParams = params

                when {
                    bookedSeats.contains(seatId) -> {
                        btn.setBackgroundColor(0xFFE53935.toInt()) // 🔴 Booked
                        btn.isEnabled = false
                    }

                    else -> {
                        btn.setBackgroundColor(0xFF7E57C2.toInt()) // 🟣 Available
                    }
                }

                btn.setOnClickListener {

                    if (selectedSeats.contains(seatId)) {
                        selectedSeats.remove(seatId)
                        btn.setBackgroundColor(0xFF7E57C2.toInt())
                    } else {
                        selectedSeats.add(seatId)
                        btn.setBackgroundColor(0xFF00C853.toInt())
                    }

                    selectedSeatsText.text =
                        if (selectedSeats.isEmpty()) "None"
                        else selectedSeats.joinToString(", ")
                }

                seatGrid.addView(btn)
            }
        }
    }

    // 🔹 Save booked seats
    private fun saveBookedSeats() {
        val prefs = getSharedPreferences("SEATS", Context.MODE_PRIVATE)
        prefs.edit().putStringSet("BOOKED", bookedSeats).apply()
    }

    // 🔹 Load booked seats
    private fun loadBookedSeats() {
        val prefs = getSharedPreferences("SEATS", Context.MODE_PRIVATE)
        val saved = prefs.getStringSet("BOOKED", emptySet())
        bookedSeats.addAll(saved!!)
    }
}
