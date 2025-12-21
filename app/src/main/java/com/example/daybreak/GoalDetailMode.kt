import com.example.daybreak.PeriodType

// GoalDetailItem.kt (예상 코드)
sealed class GoalDetailItem {
    object Header : GoalDetailItem()data class Period(
        val periodText: String,
        val description: String,
        val type: PeriodType
    ) : GoalDetailItem()

    object DeleteAction : GoalDetailItem() //  삭제 버튼
    object NextAction : GoalDetailItem()   //  다음 단계로 진행 버튼
}
