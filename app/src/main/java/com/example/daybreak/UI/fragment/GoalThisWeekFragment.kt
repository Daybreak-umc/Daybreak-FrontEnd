import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daybreak.R
import com.example.daybreak.UI.adapter.GoalThisWeekAdapter

class GoalThisWeekFragment : Fragment(R.layout.fragment_goal_this_week) {

    private lateinit var rvGoalThisWeek: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        rvGoalThisWeek = view.findViewById(R.id.rvGoalThisWeek)

        rvGoalThisWeek.layoutManager = LinearLayoutManager(requireContext())
        rvGoalThisWeek.adapter = GoalThisWeekAdapter(itemCount = 3)
    }
}
