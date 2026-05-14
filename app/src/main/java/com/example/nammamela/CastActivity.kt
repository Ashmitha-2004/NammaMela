package com.example.nammamela

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)

        // BACK BUTTON
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }

        // IMAGE URLs (replace later with Firebase)
        val actor = "https://randomuser.me/api/portraits/men/1.jpg"
        val actress = "https://randomuser.me/api/portraits/women/1.jpg"
        val comedian = "https://randomuser.me/api/portraits/men/2.jpg"
        val singer = "https://randomuser.me/api/portraits/men/3.jpg"
        val director = "https://randomuser.me/api/portraits/men/4.jpg"

        Glide.with(this).load(actor).into(findViewById(R.id.imgLeadActor))
        Glide.with(this).load(actress).into(findViewById(R.id.imgLeadActress))
        Glide.with(this).load(comedian).into(findViewById(R.id.imgComedian))
        Glide.with(this).load(singer).into(findViewById(R.id.imgSinger))
        Glide.with(this).load(director).into(findViewById(R.id.imgDirector))
    }
}
