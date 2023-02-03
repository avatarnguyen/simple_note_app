package com.example.simplenotesapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotesapp.Models.Note
import com.example.simplenotesapp.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, private val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]

        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.noteTextView.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notesLayout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor
                    (), null
            )
        )

        holder.notesLayout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }

        holder.notesLayout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.notesLayout)
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            val matchTitle = item.title?.lowercase()?.contains(search, true) == true
            val matchNote = item.note?.lowercase()?.contains(search, true) == true
            if (matchTitle || matchNote) {
                notesList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    private fun randomColor(): Int {
        val colorList = ArrayList<Int>()
        colorList.add(R.color.NoteColor1)
        colorList.add(R.color.NoteColor2)
        colorList.add(R.color.NoteColor3)
        colorList.add(R.color.NoteColor4)
        colorList.add(R.color.NoteColor5)
        colorList.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)

        return colorList[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesLayout: CardView = itemView.findViewById(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)!!
        val noteTextView = itemView.findViewById<TextView>(R.id.tv_note)!!
        val date = itemView.findViewById<TextView>(R.id.tv_date)!!
    }

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }

}