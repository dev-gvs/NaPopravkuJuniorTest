package kz.gvsx.napopravkujuniortest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.RepositoryItemBinding
import kz.gvsx.napopravkujuniortest.domain.Repository

class RepositoryAdapter : ListAdapter<Repository, RepositoryAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding: RepositoryItemBinding by viewBinding()

        fun onBind(repository: Repository) = with(viewBinding) {
            avatarImageView.load(repository.owner.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            fullNameTextView.text = repository.fullName
            loginTextView.text = repository.owner.login
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

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(getItem(position))
}