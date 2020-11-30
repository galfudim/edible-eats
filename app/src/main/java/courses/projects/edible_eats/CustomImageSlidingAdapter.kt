package courses.projects.edible_eats

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomImageSlidingAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = itemsCount
    override fun createFragment(position: Int) = CustomImageSlidingFragment.getInstance(position)
}