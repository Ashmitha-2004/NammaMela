package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var playDao: PlayDao

    lateinit var playTitle: TextView
    lateinit var playDuration: TextView
    lateinit var playImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val managerIcon = findViewById<ImageView>(R.id.managerIcon)

        managerIcon.setOnClickListener {

            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Manager Login")

            val input = android.widget.EditText(this)
            input.hint = "Enter Password"
            builder.setView(input)

            builder.setPositiveButton("Login") { _, _ ->
                if (input.text.toString() == "admin123") {
                    startActivity(Intent(this, UpdatePlayActivity::class.java))
                } else {
                    android.widget.Toast.makeText(this, "Wrong Password", android.widget.Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancel", null)
            builder.show()
        }


        val viewCastBtn = findViewById<MaterialButton>(R.id.viewCastBtn)
        val bookSeatBtn = findViewById<MaterialButton>(R.id.bookSeatBtn)
        val fanWallBtn = findViewById<MaterialButton>(R.id.fanWallBtn)

        playTitle = findViewById(R.id.playTitle)
        playDuration = findViewById(R.id.playDuration)
        playImage = findViewById(R.id.playImage)

        // Navigation
        viewCastBtn.setOnClickListener {
            startActivity(Intent(this, CastActivity::class.java))
        }

        bookSeatBtn.setOnClickListener {
            startActivity(Intent(this, SeatBookingActivity::class.java))
        }

        fanWallBtn.setOnClickListener {
            startActivity(Intent(this, FanWallActivity::class.java))
        }

        // ✅ SAME DB NAME EVERYWHERE
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "namma_mela_db"
        ).fallbackToDestructiveMigration().build()

        playDao = db.playDao()

        loadPlayData()
    }

    override fun onResume() {
        super.onResume()
        loadPlayData()
    }

    private fun loadPlayData() {
        lifecycleScope.launch {

            var play = playDao.getPlay()

            if (play == null) {
                playDao.insert(
                    Play(
                        id = 1,
                        title = "Veera Kathai",
                        duration = "2 Hours",
                        time = "7:00 PM",
                        imageUrl = ""
                    )
                )
                play = playDao.getPlay()
            }

            play?.let {
                playTitle.text = it.title
                playDuration.text = "${it.time} • ${it.duration}"

                Glide.with(this@MainActivity)
                    .load(it.imageUrl)
                    .placeholder(R.drawable.veera_kathai)
                    .error(R.drawable.veera_kathai)
                    .into(playImage)
            }
        }
    }
}
