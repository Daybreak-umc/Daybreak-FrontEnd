package com.example.daybreak.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.Data.GoalDetailItem
import com.example.daybreak.R

class GoalDetailAdapter(
    private val items: List<GoalDetailItem>,
    private val onDeleteClick: () -> Unit,
    private val onNextClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_PERIOD = 1
        private const val TYPE_DELETE = 2
        private const val TYPE_NEXT = 3
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is GoalDetailItem.Header -> TYPE_HEADER
            is GoalDetailItem.Period -> TYPE_PERIOD
            is GoalDetailItem.DeleteAction -> TYPE_DELETE
            is GoalDetailItem.NextAction -> TYPE_NEXT
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_goal_detail_header, parent, false)
                HeaderViewHolder(view)
            }

            TYPE_PERIOD -> {
                val view = inflater.inflate(R.layout.item_goal_period, parent, false)
                PeriodViewHolder(view)
            }

            TYPE_DELETE -> {
                val view = inflater.inflate(R.layout.item_goal_detail_action_delete, parent, false)
                DeleteViewHolder(view, onDeleteClick)
            }

            TYPE_NEXT -> {
                val view = inflater.inflate(R.layout.item_goal_detail_action_next, parent, false)
                NextViewHolder(view, onNextClick)
            }

            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is GoalDetailItem.Header -> Unit // 정적 UI, 바인딩 없음
            is GoalDetailItem.Period -> (holder as PeriodViewHolder).bind(item) // 🔥 핵심 부분
            is GoalDetailItem.DeleteAction -> Unit // 클릭만 있음
            is GoalDetailItem.NextAction -> Unit // 클릭만 있음
        }
    }

    // ---------- ViewHolder들 ----------

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // 🔥 가장 많이 변경된 부분
    class PeriodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rootLayout: View = view // 카드 전체 배경을 위해 root 뷰 잡기
        private val periodChip: TextView = view.findViewById(R.id.item_PeriodChip_Tv)
        private val description: TextView = view.findViewById(R.id.item_Description_Tv)

        fun bind(item: GoalDetailItem.Period) {
            // 1. 텍스트 설정
            periodChip.text = item.periodText
            description.text = item.description

            // Enum에서 디자인 정보 꺼내오기
            val type = item.type

            rootLayout.setBackgroundResource(type.cardBackgroundRes)

            // 칩 배경 적용
            periodChip.setBackgroundResource(type.chipBackgroundRes)

            // 4. 스타일(폰트, 글자색 등) 적용
            TextViewCompat.setTextAppearance(periodChip, type.chipStyleRes)
            TextViewCompat.setTextAppearance(description, type.descStyleRes)
        }
    }

    class DeleteViewHolder(
        view: View,
        onDeleteClick: () -> Unit
    ) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onDeleteClick() }
        }
    }

    class NextViewHolder(
        view: View,
        onNextClick: () -> Unit
    ) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onNextClick() }
        }
    }
}