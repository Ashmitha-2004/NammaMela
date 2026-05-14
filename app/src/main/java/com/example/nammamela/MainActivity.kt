package com.example.nammamela

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var playDao: PlayDao

    private lateinit var playTitle: TextView
    private lateinit var playDuration: TextView
    private lateinit var playImage: ImageView
    private lateinit var profileIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewCastBtn =
            findViewById<MaterialButton>(R.id.viewCastBtn)

        val bookSeatBtn =
            findViewById<MaterialButton>(R.id.bookSeatBtn)

        val fanWallBtn =
            findViewById<MaterialButton>(R.id.fanWallBtn)

        profileIcon =
            findViewById(R.id.profileIcon)

        playTitle =
            findViewById(R.id.playTitle)

        playDuration =
            findViewById(R.id.playDuration)

        playImage =
            findViewById(R.id.playImage)

        db = AppDatabase.getDatabase(this)
        playDao = db.playDao()

        viewCastBtn.setOnClickListener {
            startActivity(
                Intent(this, CastActivity::class.java)
            )
        }

        fanWallBtn.setOnClickListener {
            startActivity(
                Intent(this, FanWallActivity::class.java)
            )
        }

        bookSeatBtn.setOnClickListener {

            lifecycleScope.launch {

                val play = playDao.getPlay()

                val intent = Intent(
                    this@MainActivity,
                    SeatBookingActivity::class.java
                )

                intent.putExtra(
                    "playName",
                    play?.title ?: "No Play Updated"
                )

                intent.putExtra(
                    "showTime",
                    play?.time ?: "-"
                )

                startActivity(intent)
            }
        }

        // PROFILE MENU
        profileIcon.setOnClickListener {

            val popup =
                PopupMenu(this, profileIcon)

            popup.menu.add("Logout")

            popup.setOnMenuItemClickListener {

                // VERY IMPORTANT
                getSharedPreferences(
                    "NammaMelaUser",
                    MODE_PRIVATE
                )
                    .edit()
                    .putBoolean("loggedIn", false)
                    .apply()

                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish()

                true
            }

            popup.show()
        }

        loadPlayData()
    }

    override fun onResume() {
        super.onResume()
        loadPlayData()
    }

    private fun loadPlayData() {

        lifecycleScope.launch {

            val play = playDao.getPlay()

            play?.let {

                runOnUiThread {

                    playTitle.text = it.title

                    playDuration.text =
                        "${it.time} • ${it.duration}"

                    Glide.with(this@MainActivity)
                        .load(it.imageUrl)
                        .placeholder(
                            R.drawable.veera_kathai
                        )
                        .error(
                            R.drawable.veera_kathai
                        )
                        .into(playImage)
                }
            }
        }
    }
}