package courses.projects.edible_eats

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CustomImageSlidingAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    // Utilized https://blog.usejournal.com/how-to-auto-scroll-viewpager2-in-android-672e6dcee13d
    // to assist with this feature
    override fun getItemCount(): Int {
        return itemsCount
    }
    override fun createFragment(position: Int): Fragment {
        return CustomImageSlidingFragment.getInstance(position)
    }
}