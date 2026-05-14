package com.example.nammamela

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var castDao: CastDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.castRecycler)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        castDao = AppDatabase.getDatabase(this).castDao()
    }

    override fun onResume() {
        super.onResume()
        loadCast()
    }

    private fun loadCast() {
        lifecycleScope.launch {
            val castList = castDao.getAll()

            runOnUiThread {
                recyclerView.adapter = CastAdapter(castList)
            }
        }
    }
}