package com.example.nammamela

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FanWallActivity : AppCompatActivity() {

    private lateinit var commentRecycler: RecyclerView
    private lateinit var totalApplause: TextView
    private lateinit var commentInput: EditText
    private lateinit var sendBtn: Button

    private val commentList = ArrayList<String>()
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan_wall)

        // Bind views
        commentRecycler = findViewById(R.id.commentRecycler)
        totalApplause = findViewById(R.id.totalApplause)
        commentInput = findViewById(R.id.commentInput)
        sendBtn = findViewById(R.id.sendBtn)

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener { finish() }

        // Setup Recycler
        adapter = CommentAdapter(commentList)
        commentRecycler.layoutManager = LinearLayoutManager(this)
        commentRecycler.adapter = adapter

        // Load saved comments
        loadComments()

        // Post button
        sendBtn.setOnClickListener {
            val text = commentInput.text.toString().trim()

            if (text.isNotEmpty()) {
                commentList.add(text)
                adapter.notifyItemInserted(commentList.size - 1)
                commentInput.text.clear()

                saveComments()
                updateApplause()
            }
        }
    }

    private fun loadComments() {
        val prefs = getSharedPreferences("fanwall", MODE_PRIVATE)
        val savedSet = prefs.getStringSet("comments", HashSet())!!

        commentList.clear()
        commentList.addAll(savedSet)

        adapter.notifyDataSetChanged()
        updateApplause()
    }

    private fun saveComments() {
        val prefs = getSharedPreferences("fanwall", MODE_PRIVATE)
        val set = HashSet(commentList) // convert list → set
        prefs.edit().putStringSet("comments", set).apply()
    }

    private fun updateApplause() {
        totalApplause.text = "👏 Total Applause: ${commentList.size}"
    }
}
