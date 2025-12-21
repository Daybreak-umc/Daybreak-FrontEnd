package com.example.daybreak.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.Data.GoalItemData
import com.example.daybreak.R

class GoalThisWeekAdapter(
    private var items: List<GoalItemData> = emptyList()
) : RecyclerView.Adapter<GoalThisWeekAdapter.GoalViewHolder>() {

    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 아이템 XML에 있는 ID와 정확히 일치해야 함
        val tvChip: TextView = itemView.findViewById(R.id.item_this_week_PeriodChip_Tv)
        val tvDescription: TextView = itemView.findViewById(R.id.item_this_WeekDescription_Tv)

        fun bind(item: GoalItemData, position: Int) {

            // 순서에 따라 칩 텍스트 변경
            val orderText = when (position) {
                0 -> "첫 번째 목표"
                1 -> "두 번째 목표"
                2 -> "세 번째 목표"
                3 -> "네 번째 목표"
                4 -> "다섯 번째 목표"
                5 -> "여섯 번째 목표"
                6 -> "일곱 번째 목표"
                7 -> "여덟 번째 목표"
                8 -> "아홉 번째 목표"
                9 -> "열 번째 목표"
                else -> "${position + 1} 번째 목표"
            }

            tvChip.text = orderText
            tvDescription.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal_this_week_period, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<GoalItemData>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}
