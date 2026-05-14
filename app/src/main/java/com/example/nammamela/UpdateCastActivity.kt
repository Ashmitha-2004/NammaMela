package com.example.nammamela

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateCastActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var castDao: CastDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_cast)

        val name = findViewById<EditText>(R.id.editName)
        val role = findViewById<EditText>(R.id.editRole)
        val image = findViewById<EditText>(R.id.editImage)
        val save = findViewById<Button>(R.id.saveBtn)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "seat_db"
        ).fallbackToDestructiveMigration().build()

        castDao = db.castDao()

        save.setOnClickListener {

            val nameText = name.text.toString().trim()
            val roleText = role.text.toString().trim()
            val imageText = image.text.toString().trim()

            if (nameText.isEmpty() || roleText.isEmpty() || imageText.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                // ✅ Run DB operation in background thread
                withContext(Dispatchers.IO) {
                    castDao.insert(
                        Cast(
                            name = nameText,
                            role = roleText,
                            imageUrl = imageText
                        )
                    )
                }

                // ✅ Back on main thread → safe UI updates
                Toast.makeText(this@UpdateCastActivity, "Cast Added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
