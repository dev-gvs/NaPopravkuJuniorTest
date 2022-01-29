package kz.gvsx.napopravkujuniortest.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.DetailsFragmentBinding

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val viewBinding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.textView.text = viewModel.id.toString()
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
        const val REPOSITORY_ID_KEY = "KEY_REPOSITORY_ID"

        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(REPOSITORY_ID_KEY, id)
            }
        }
    }
}