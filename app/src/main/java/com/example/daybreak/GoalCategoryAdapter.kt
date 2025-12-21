package com.example.daybreak

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.databinding.ItemGoalCategoryBinding

class GoalCategoryAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<GoalCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemGoalCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvCategory.text = item
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGoalCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
