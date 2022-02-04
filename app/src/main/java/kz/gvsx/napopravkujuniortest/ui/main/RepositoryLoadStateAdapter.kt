package kz.gvsx.napopravkujuniortest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.LoadStateItemBinding

class RepositoryLoadStateAdapter(
    private val onRetry: () -> Unit
) : LoadStateAdapter<RepositoryLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(view: View, onRetry: () -> Unit) : RecyclerView.ViewHolder(view) {
        private val viewBinding: LoadStateItemBinding by viewBinding()

        init {
            viewBinding.retryButton.setOnClickListener { onRetry() }
        }

        fun onBind(loadState: LoadState) = with(viewBinding) {
            progressIndicator.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorTextView.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_item, parent, false)

        return LoadStateViewHolder(view, onRetry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.onBind(loadState)
}