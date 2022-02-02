package kz.gvsx.napopravkujuniortest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.clear
import coil.load
import coil.transform.CircleCropTransformation
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.RepositoryItemBinding
import kz.gvsx.napopravkujuniortest.domain.Repository

class RepositoryAdapter(private val onClick: (Repository) -> Unit) :
    PagingDataAdapter<Repository, RepositoryAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        private val viewBinding: RepositoryItemBinding by viewBinding()

        init {
            viewBinding.repository.setOnClickListener {
                val adapterPos = bindingAdapterPosition
                if (adapterPos != RecyclerView.NO_POSITION) clickAtPosition(adapterPos)
            }
        }

        fun onBind(repository: Repository) = with(viewBinding) {
            avatarImageView.load(repository.owner.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            fullNameTextView.text = repository.fullName
            loginTextView.text = repository.owner.login
            if (kz.gvsx.napopravkujuniortest.BuildConfig.DEBUG)
                idTextView.text = repository.id.toString()
        }

        fun clear() = with(viewBinding) {
            avatarImageView.clear()
            fullNameTextView.text = ""
            loginTextView.text = ""
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_item, parent, false)

        return ViewHolder(view) { position ->
            getItem(position)?.let { repository ->
                onClick(repository)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        if (repository != null)
            holder.onBind(repository)
        else
            holder.clear()
    }
}