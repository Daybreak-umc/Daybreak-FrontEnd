package com.example.daybreak

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.databinding.ItemWeeklygoalsBinding

class WeeklyGoalRVAdapter(private val items: List<WeeklyGoalData>) :
RecyclerView.Adapter<WeeklyGoalRVAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding: ItemWeeklygoalsBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(item: WeeklyGoalData){
            val context = binding.root.context

            //CategoryMapper를 사용해 색상 가져오기
            val color = CategoryMapper.getColor(context,item.categoryId)


            //XML 에 데이터 및 색상 적용
            binding.icGoalitemBar.imageTintList = ColorStateList.valueOf(color)
            binding.goalitemTypeTv.setTextColor(color)
            binding.goalitemTypeTv.text = item.typeName
            binding.goalitemMessageTv.text = item.message

            if (item.checkList.size >= 1) binding.checkText1.text = item.checkList[0].text
            if (item.checkList.size >= 2) binding.checkText2.text = item.checkList[1].text
            if (item.checkList.size >= 3) binding.checkText3.text = item.checkList[2].text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemWeeklygoalsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}