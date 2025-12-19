import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.R

class GoalDetailFragment : Fragment(R.layout.fragment_goal_detail) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.goal_detail_Rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(false)

        adapter = GoalDetailAdapter(
            items = createItems(),
            onDeleteClick = { onDeleteGoalClicked() },
            onNextClick = { /* 여기서는 사용 안 함 */ }
        )

        recyclerView.adapter = adapter
    }

    /**
     * 최초 화면 구성
     * - 헤더
     * - 기간 카드들
     * - 목표 삭제하기 (ONLY)
     */
    private fun createItems(): List<GoalDetailItem> {
        return listOf(
            GoalDetailItem.Header,

            GoalDetailItem.Period(
                periodText = "일 년",
                description = "운동이 일상의 일부가 됩니다."
            ),
            GoalDetailItem.Period(
                periodText = "여섯 달",
                description = "주 3-4회 운동 리듬이 잡히기 시작합니다."
            ),
            GoalDetailItem.Period(
                periodText = "세 달",
                description = "운동 습관이 형성됩니다."
            ),

            // ✅ 최초 화면에서는 이것만 노출
            GoalDetailItem.DeleteAction
        )
    }

    private fun onDeleteGoalClicked() {
        // TODO: 삭제 확인 다이얼로그
        Toast.makeText(requireContext(), "목표 삭제 클릭", Toast.LENGTH_SHORT).show()
    }
}
