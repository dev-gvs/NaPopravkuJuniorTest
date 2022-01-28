package kz.gvsx.napopravkujuniortest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import kz.gvsx.napopravkujuniortest.R
import kz.gvsx.napopravkujuniortest.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewBinding: MainFragmentBinding by viewBinding()
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewBinding.message.text = "Main Fragment Text"
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}