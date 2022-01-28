package kz.gvsx.napopravkujuniortest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.MainFragmentBinding
import kz.gvsx.napopravkujuniortest.launchAndRepeatWithViewLifecycle
import kz.gvsx.napopravkujuniortest.px
import kz.gvsx.napopravkujuniortest.ui.details.DetailsFragment

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val adapter get() = viewBinding.recyclerView.adapter as RepositoryAdapter
    private val viewBinding: MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding.recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = RepositoryAdapter { repository ->
                navigateToRepositoryDetails(repository.id)
            }
            val divider = MaterialDividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
            ).apply {
                dividerInsetStart = 16.px
                dividerInsetEnd = 16.px
            }
            addItemDecoration(divider)
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.repositoriesPagingFlow.collect(adapter::submitData)
        }
    }

    private fun navigateToRepositoryDetails(id: Int) = parentFragmentManager.commit {
        replace(R.id.container, DetailsFragment.newInstance(id), DetailsFragment.TAG)
        setReorderingAllowed(true)
        addToBackStack(DetailsFragment.TAG)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
