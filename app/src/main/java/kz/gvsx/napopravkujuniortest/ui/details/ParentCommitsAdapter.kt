package kz.gvsx.napopravkujuniortest.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.ParentCommitItemBinding

class ParentCommitsAdapter : ListAdapter<String, ParentCommitsAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding: ParentCommitItemBinding by viewBinding()

        fun onBind(sha: String) = with(viewBinding) {
            shaTextView.text = sha
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_commit_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(getItem(position))
}