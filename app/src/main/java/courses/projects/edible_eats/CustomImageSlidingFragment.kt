package courses.projects.edible_eats

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class CustomImageSlidingFragment : Fragment(R.layout.fragment_sliding_image) {
    // Utilized https://blog.usejournal.com/how-to-auto-scroll-viewpager2-in-android-672e6dcee13d
    // to assist with this feature
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(POSITION)
        val icons = requireContext().resources.obtainTypedArray(R.array.images_array)

        val mSlidingImage = view.findViewById<ImageView>(R.id.sliding_image)
        mSlidingImage.setImageDrawable(icons.getDrawable(position))

    }

    companion object {
        const val POSITION = "Position"

        fun getInstance(position: Int): Fragment {
            val fragment = CustomImageSlidingFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }
}