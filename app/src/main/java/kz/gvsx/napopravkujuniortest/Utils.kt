package kz.gvsx.napopravkujuniortest

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadType
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Convert `dp` value to the `px`.
 */
inline val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

/**
 * Create a [ConcatAdapter] with the provided [LoadStateAdapter]s displaying the
 * [LoadType.REFRESH] (for initial load) and [LoadType.APPEND] [LoadState]s as
 * list items at the start and end respectively.
 *
 * @see LoadStateAdapter
 * @see PagingDataAdapter.withLoadStateHeaderAndFooter
 */
fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withHeaderAndFooter(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }
    return ConcatAdapter(header, this, footer)
}