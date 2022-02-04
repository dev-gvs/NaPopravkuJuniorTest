package kz.gvsx.napopravkujuniortest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.DetailsFragmentBinding
import kz.gvsx.napopravkujuniortest.domain.Commit
import kz.gvsx.napopravkujuniortest.domain.Repository
import kz.gvsx.napopravkujuniortest.launchAndRepeatWithViewLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val adapter get() = viewBinding.lastCommit.parentHashes.adapter as ParentCommitsAdapter
    private val viewBinding: DetailsFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(viewBinding) {
            repository.root.isClickable = false
            loadState.retryButton.setOnClickListener {
                loadAvatar()
                viewModel.fetchLastCommit()
            }
            lastCommit.parentHashes.adapter = ParentCommitsAdapter()
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedRepository.let { repository ->
            with(viewBinding.repository) {
                loadAvatar()
                fullNameTextView.text = repository.fullName
                fullNameTextView.isSelected = true
                loginTextView.text = repository.owner.login
                if (kz.gvsx.napopravkujuniortest.BuildConfig.DEBUG) {
                    idTextView.text = repository.id.toString()
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collectLatest { uiState ->
                if (uiState.lastCommit != null) {
                    viewBinding.loadState.root.isVisible = false
                    setLastCommit(uiState.lastCommit)
                } else {
                    viewBinding.lastCommit.root.isVisible = false
                    setLoadState(uiState)
                }
            }
        }
    }

    private fun loadAvatar() = with(viewBinding.repository.avatarImageView) {
        load(viewModel.selectedRepository.owner.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    private fun setLastCommit(commit: Commit) = with(viewBinding.lastCommit) {
        authorTextView.text = commit.authorName
        dateTextView.text = dateFormatter.format(Instant.parse(commit.date))
        messageTextView.text = commit.message


        if (commit.parentHashes.isEmpty()) {
            divider.visibility = View.INVISIBLE
            parentHashes.isVisible = false
        } else
            adapter.submitList(commit.parentHashes)

        root.isVisible = true
    }

    private fun setLoadState(state: DetailsUiState) = with(viewBinding.loadState) {
        progressIndicator.isVisible = state.isFetchingLastCommit
        retryButton.isVisible = state.hasErrors
        errorTextView.isVisible = state.hasErrors

        root.isVisible = true
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
        const val REPOSITORY_KEY = "KEY_REPOSITORY"

        private val dateFormatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy")
            .withZone(ZoneId.systemDefault())

        fun newInstance(repository: Repository) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_KEY, repository)
            }
        }
    }
}