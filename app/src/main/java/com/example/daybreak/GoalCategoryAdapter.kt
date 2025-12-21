package com.example.daybreak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class GoalCategoryAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<GoalCategoryAdapter.VH>() {

    private var selected: String? = null

    fun setSelected(value: String?) {
        selected = value
        notifyDataSetChanged()
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal_category, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tv.text = item

        val isSelected = item == selected

        holder.itemView.setBackgroundResource(
            if (isSelected) R.drawable.bg_rounded_primary100_16dp else 0
        )

        holder.tv.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isSelected) R.color.Primary_500 else R.color.Gray_700
            )
        )

        holder.itemView.setOnClickListener {
            selected = item
            notifyDataSetChanged()
            onClick(item)
        }
    }

    override fun getItemCount() = items.size
}
