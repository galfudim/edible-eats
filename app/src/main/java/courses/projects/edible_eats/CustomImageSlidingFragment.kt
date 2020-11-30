package courses.projects.edible_eats

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class CustomImageSlidingFragment : Fragment(R.layout.fragment_sliding_image) {
    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val fragment = CustomImageSlidingFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)
        val icons = requireContext().resources.obtainTypedArray(R.array.images_array)
        view.findViewById<ImageView>(R.id.sliding_image).setImageDrawable(icons.getDrawable(position))
    }
}