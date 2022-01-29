package kz.gvsx.napopravkujuniortest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kz.gvsx.napopravkujuniortest.MainActivity
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.MainFragmentBinding
import kz.gvsx.napopravkujuniortest.launchAndRepeatWithViewLifecycle
import kz.gvsx.napopravkujuniortest.px
import kz.gvsx.napopravkujuniortest.ui.details.DetailsFragment

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val adapter: RepositoryAdapter =
        RepositoryAdapter { navigateToRepositoryDetails(it.id) }
    private val viewBinding: MainFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding.recyclerView.apply {
            adapter = this@MainFragment.adapter
            val divider = MaterialDividerItemDecoration(
                this.context, LinearLayoutManager.VERTICAL
            ).apply {
                dividerInsetStart = 16.px
                dividerInsetEnd = 16.px
            }
            addItemDecoration(divider)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.repositoriesPagingFlow.collect(adapter::submitData)
        }
    }

    override fun onDestroyView() {
        viewBinding.recyclerView.adapter = null
        super.onDestroyView()
    }

    private fun navigateToRepositoryDetails(id: Int) = parentFragmentManager.commit {
        replace(R.id.fragmentContainer, DetailsFragment.newInstance(id), DetailsFragment.TAG)
        setReorderingAllowed(true)
        addToBackStack(DetailsFragment.TAG)
        (activity as MainActivity).enableToolbarNavigationIcon()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
