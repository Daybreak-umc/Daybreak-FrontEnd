package com.example.daybreak.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.R

class GoalThisWeekAdapter(
    private val itemCount: Int
) : RecyclerView.Adapter<GoalThisWeekAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal_this_week_period, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        // 아직 데이터 없음 → 비워둠
    }

    override fun getItemCount(): Int = itemCount

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view)
}