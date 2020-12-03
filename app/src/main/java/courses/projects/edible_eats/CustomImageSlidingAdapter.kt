package courses.projects.edible_eats

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomImageSlidingAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }
    override fun createFragment(position: Int): Fragment {
        return CustomImageSlidingFragment.getInstance(position)
    }
}