package com.timistired.notes.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timistired.notes.databinding.RvItemOverviewBinding

class OverviewAdapter(
    private val onItemSelected: (Long) -> Unit
) : ListAdapter<NotePreview, OverviewAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemOverviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RvItemOverviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notePreview: NotePreview) {
            binding.textViewNoteHeader.text = notePreview.header
            itemView.setOnClickListener {
                onItemSelected(notePreview.id)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NotePreview>() {
            override fun areItemsTheSame(oldItem: NotePreview, newItem: NotePreview): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NotePreview, newItem: NotePreview): Boolean {
                return oldItem == newItem
            }
        }
    }
}