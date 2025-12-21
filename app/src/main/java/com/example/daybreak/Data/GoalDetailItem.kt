package com.example.daybreak.Data

import com.example.daybreak.R

sealed class GoalDetailItem {
    object Header : GoalDetailItem()

    data class Period(
        val periodText: String,
        val description: String,
        val type: PeriodType
    ) : GoalDetailItem()

    object DeleteAction : GoalDetailItem()
    object NextAction : GoalDetailItem()
}

// ✅ 디자인 중앙 통제소 (Enum)
enum class PeriodType(
    // 1. 카드 배경 (Drawable)
    val cardBackgroundRes: Int,

    // 2. 칩 스타일 (Drawable + Style)
    val chipBackgroundRes: Int,
    val chipStyleRes: Int,       // 폰트, 크기, 색상 포함된 스타일

    // 3. 설명 텍스트 스타일 (Style 하나로 통합 추천)
    val descStyleRes: Int        // 설명 부분의 폰트, 크기, 색상 포함된 스타일
) {
    //  1년
    YEAR(
        cardBackgroundRes = R.drawable.bg_rounded_gray800_8dp,
        chipBackgroundRes = R.drawable.bg_rounded_gray900_16dp,

        // 칩 텍스트 스타일
        chipStyleRes = R.style.wantedSans_regular_12sp_gray0,

        // 설명 텍스트 스타일
        descStyleRes = R.style.wantedSans_semibold_14sp_select
    ),

    // 6개월
    SIX_MONTH(
        cardBackgroundRes = R.drawable.bg_rounded_gray600_8dp,
        chipBackgroundRes = R.drawable.bg_rounded_primary100_16dp,

        // 칩 스타일
        chipStyleRes = R.style.wantedSans_regular_12sp_primary500,

        // 설명 스타일
        descStyleRes = R.style.wantedSans_semibold_14sp_select
    ),

    // 3개월
    THREE_MONTH(
        cardBackgroundRes = R.drawable.bg_rounded_gray400_8dp,
        chipBackgroundRes = R.drawable.bg_rounded_primary500_16dp,

        chipStyleRes = R.style.wantedSans_regular_12sp_gray0, // 흰 글씨
        descStyleRes = R.style.wantedSans_semibold_14sp_bodygray // 회색 글씨
    ),

    // 1개월
    MONTH(
        cardBackgroundRes = R.drawable.bg_rounded_gray0_8dp,
        chipBackgroundRes = R.drawable.bg_rounded_primary500_16dp,

        chipStyleRes = R.style.wantedSans_semibold_14sp_select,
        descStyleRes = R.style.wantedSans_semibold_12sp_gray1000
    ),

    // 1주
    WEEK(
        cardBackgroundRes = R.drawable.bg_rounded_gray0_8dp,
        chipBackgroundRes = R.drawable.bg_rounded_primary500_16dp,

        chipStyleRes = R.style.wantedSans_semibold_14sp_select,
        descStyleRes = R.style.wantedSans_semibold_12sp_gray1000
    );
}