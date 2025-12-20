sealed class GoalDetailItem {

    /** 상단 헤더 */
    object Header : GoalDetailItem()

    /** 기간 카드 */
    data class Period(
        val periodText: String,
        val description: String
    ) : GoalDetailItem()

    /** "목표 삭제하기" 액션 */
    object DeleteAction : GoalDetailItem()

    /** "다음으로" 버튼 액션 */
    object NextAction : GoalDetailItem()
}
