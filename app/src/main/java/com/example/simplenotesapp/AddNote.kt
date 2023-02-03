package com.example.simplenotesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.simplenotesapp.Models.Note
import com.example.simplenotesapp.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdated = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()

            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdated) {
                    note = Note(
                        oldNote.id, title, noteDesc, formatter.format(Date())
                    )
                } else {
                    note = Note(
                        null, title, noteDesc, formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("current_note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNote, "Title can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}