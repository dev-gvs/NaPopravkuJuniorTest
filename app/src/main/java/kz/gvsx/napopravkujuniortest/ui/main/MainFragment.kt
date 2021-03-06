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
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.MainFragmentBinding
import kz.gvsx.napopravkujuniortest.domain.Repository
import kz.gvsx.napopravkujuniortest.launchAndRepeatWithViewLifecycle
import kz.gvsx.napopravkujuniortest.px
import kz.gvsx.napopravkujuniortest.ui.details.DetailsFragment
import kz.gvsx.napopravkujuniortest.withHeaderAndFooter

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val adapter = RepositoryAdapter(onClick = ::navigateToRepositoryDetails)
    private val adapterWithHeaderAndFooter = adapter.withHeaderAndFooter(
        header = RepositoryLoadStateAdapter(onRetry = adapter::retry),
        footer = RepositoryLoadStateAdapter(onRetry = adapter::retry),
    )
    private val viewBinding: MainFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(viewBinding.recyclerView) {
            adapter = adapterWithHeaderAndFooter
            // Because this Recycler View has match_parent width and height,
            // for optimization purposes it's better to setHasFixedSize to true.
            setHasFixedSize(true)
            val divider = MaterialDividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
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
        // Prevents memory leak.
        viewBinding.recyclerView.adapter = null
        super.onDestroyView()
    }

    private fun navigateToRepositoryDetails(repository: Repository) = parentFragmentManager.commit {
        setReorderingAllowed(true)
        setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        replace(
            R.id.fragmentContainer,
            DetailsFragment.newInstance(repository),
            DetailsFragment.TAG
        )
        addToBackStack(DetailsFragment.TAG)
    }
}
