package com.example.daybreak.UI.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.Api.ApiClient
import com.example.daybreak.Data.CheckItem
import com.example.daybreak.UI.`object`.CategoryMapper
import com.example.daybreak.Data.WeeklyGoalData
import com.example.daybreak.databinding.ItemWeeklygoalsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeeklyGoalRVAdapter(
    private val items: List<WeeklyGoalData>,
    private val onItemClick: (WeeklyGoalData) -> Unit = {}
) : RecyclerView.Adapter<WeeklyGoalRVAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemWeeklygoalsBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: WeeklyGoalData){
            val context = binding.root.context

            // 1. 색상 및 텍스트 데이터 적용
            val color = CategoryMapper.getColor(context, item.categoryId)
            binding.icGoalitemBar.imageTintList = ColorStateList.valueOf(color)
            binding.goalitemTypeTv.setTextColor(color)
            binding.goalitemTypeTv.text = item.typeName
            binding.goalitemMessageTv.text = item.message

            // 2. 체크리스트 텍스트 설정
            if (item.checkList.size >= 1) binding.checkText1.text = item.checkList[0].text
            if (item.checkList.size >= 2) binding.checkText2.text = item.checkList[1].text
            if (item.checkList.size >= 3) binding.checkText3.text = item.checkList[2].text

            // 3. 체크박스 로직 및 API 통신 연결
            val checkIcons = listOf(binding.checkIcon1, binding.checkIcon2, binding.checkIcon3)
            val checkRows = listOf(binding.checkRow1, binding.checkRow2, binding.checkRow3)

            item.checkList.forEachIndexed { index, checkItem ->
                if (index < checkIcons.size) {
                    checkIcons[index].isSelected = checkItem.isChecked

                    checkIcons[index].setOnClickListener {
                        val nextStatus = !checkItem.isChecked

                        // [낙관적 업데이트] UI 먼저 변경
                        checkItem.isChecked = nextStatus
                        checkIcons[index].isSelected = nextStatus

                        // [API 호출] 서버에 완료 상태 전송
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                // missionId로 checkItem.id(Long)를 전달
                                val response = ApiClient.missionService.completeMission(checkItem.id)

                                if (!response.isSuccessful) {
                                    withContext(Dispatchers.Main) {
                                        // 서버 실패 시 롤백 및 에러 표시
                                        revertStatus(checkItem, checkIcons[index])
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    // 네트워크 오류 시 롤백 (이미지 7번 상황)
                                    revertStatus(checkItem, checkIcons[index])
                                }
                            }
                        }
                    }
                }
            }

            // 4. 아이템 전체 클릭 리스너 (상세 페이지 이동 등)
            val itemClickListener = View.OnClickListener {
                onItemClick(item)
            }
            binding.root.setOnClickListener(itemClickListener)
            binding.goalitemTypeTv.setOnClickListener(itemClickListener)
            binding.goalitemMessageTv.setOnClickListener(itemClickListener)
            binding.goalitemNextIv.setOnClickListener(itemClickListener)
            binding.icGoalitemBar.setOnClickListener(itemClickListener)
        }

        // 통신 실패 시 원래대로 되돌리는 함수
        private fun revertStatus(checkItem: CheckItem, icon: View) {
            checkItem.isChecked = !checkItem.isChecked
            icon.isSelected = checkItem.isChecked
            Toast.makeText(binding.root.context, "통신 중 오류가 발생했어요", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeeklygoalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}