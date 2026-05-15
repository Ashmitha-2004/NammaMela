package com.example.nammamela

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var seatGrid: GridLayout
    private lateinit var selectedSeatsText: TextView
    private lateinit var seatsLeftText: TextView
    private lateinit var confirmBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var generateTicketBtn: Button

    private lateinit var db: AppDatabase
    private lateinit var seatDao: SeatDao

    private val selectedSeats = mutableListOf<String>()
    private val bookedSeats = mutableSetOf<String>()
    private val mySeats = mutableSetOf<String>()

    private lateinit var showKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        val playName = intent.getStringExtra("playName") ?: ""
        val time = intent.getStringExtra("showTime") ?: ""

        showKey = "${playName}_${time}"

        db = AppDatabase.getDatabase(this)
        seatDao = db.seatDao()

        seatGrid = findViewById(R.id.seatGrid)
        selectedSeatsText = findViewById(R.id.tvSelectedSeats)
        seatsLeftText = findViewById(R.id.tvSeatsLeft)

        confirmBtn = findViewById(R.id.btnConfirm)
        cancelBtn = findViewById(R.id.btnCancel)
        generateTicketBtn = findViewById(R.id.generateTicketBtn)

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }

        loadSeats()

        confirmBtn.setOnClickListener { confirmBooking() }
        cancelBtn.setOnClickListener { cancelBooking() }

        generateTicketBtn.setOnClickListener {

            if (mySeats.isEmpty()) {
                Toast.makeText(this, "Please book seats first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ticketIntent = Intent(this, TicketActivity::class.java)

            ticketIntent.putExtra("play", playName)
            ticketIntent.putExtra("seat", mySeats.joinToString(", "))
            ticketIntent.putExtra("time", time)

            startActivity(ticketIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadSeats()
    }

    private fun loadSeats() {
        lifecycleScope.launch {

            val allSeats = seatDao.getSeats(showKey)

            bookedSeats.clear()
            mySeats.clear()

            bookedSeats.addAll(allSeats.filter { it.isBooked }.map { it.seatId })
            mySeats.addAll(allSeats.filter { it.isBooked && it.bookedByUser }.map { it.seatId })

            runOnUiThread {
                generateTicketBtn.visibility =
                    if (mySeats.isNotEmpty()) View.VISIBLE else View.GONE

                refreshGrid()
            }
        }
    }

    private fun refreshGrid() {
        seatGrid.removeAllViews()
        createSeats()

        selectedSeatsText.text =
            if (selectedSeats.isEmpty()) "None"
            else selectedSeats.joinToString(", ")

        seatsLeftText.text =
            "Seats Left: ${25 - bookedSeats.size}"
    }

    private fun createSeats() {

        val rows = listOf("A", "B", "C", "D", "E")

        for (row in rows) {
            for (i in 1..5) {

                val seatId = "$row$i"

                val btn = Button(this)
                btn.text = seatId

                val params = GridLayout.LayoutParams()
                params.width = 0
                params.height = 120
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                params.setMargins(4, 4, 4, 4)
                btn.layoutParams = params

                when {
                    selectedSeats.contains(seatId) ->
                        btn.setBackgroundColor(Color.GREEN)

                    bookedSeats.contains(seatId) ->
                        btn.setBackgroundColor(Color.RED)

                    else ->
                        btn.setBackgroundColor(Color.parseColor("#7E57C2"))
                }

                btn.setOnClickListener {

                    if (bookedSeats.contains(seatId)) {
                        Toast.makeText(this, "Seat already booked", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if (selectedSeats.contains(seatId))
                        selectedSeats.remove(seatId)
                    else
                        selectedSeats.add(seatId)

                    refreshGrid()
                }

                seatGrid.addView(btn)
            }
        }
    }

    private fun confirmBooking() {

        if (selectedSeats.isEmpty()) {
            Toast.makeText(this, "Select seat first", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {

            selectedSeats.forEach {

                seatDao.insert(
                    Seat(
                        seatId = it,
                        showKey = showKey,
                        isBooked = true,
                        bookedByUser = true
                    )
                )
            }

            runOnUiThread {
                Toast.makeText(this@SeatBookingActivity, "Booking Confirmed", Toast.LENGTH_SHORT).show()
            }

            selectedSeats.clear()
            loadSeats()
        }
    }

    private fun cancelBooking() {

        if (mySeats.isEmpty()) {
            Toast.makeText(this, "No booking to cancel", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {

            mySeats.forEach {

                seatDao.insert(
                    Seat(
                        seatId = it,
                        showKey = showKey,
                        isBooked = false,
                        bookedByUser = false
                    )
                )
            }

            selectedSeats.clear()

            runOnUiThread {
                Toast.makeText(this@SeatBookingActivity, "Booking Cancelled", Toast.LENGTH_SHORT).show()
            }

            loadSeats()
        }
    }
}